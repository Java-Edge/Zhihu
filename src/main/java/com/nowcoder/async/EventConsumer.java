package com.nowcoder.async;

import com.alibaba.fastjson.JSON;
import com.nowcoder.util.JedisAdapter;
import com.nowcoder.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将事件取出来,分到不同的关联的handler
 *
 * @author Shusheng Shi
 * @since 2017/8/11 21:30
 */
@Service
public class EventConsumer implements InitializingBean,ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    @Autowired
    private JedisAdapter jedisAdapter;

    //一类事件对应多种的handler
    private Map<EventType, List<EventHandler>> config = new HashMap<>();

    private ApplicationContext applicationContext;

    //此类需要早早地启动执行
    @Override
    public void afterPropertiesSet() throws Exception {
        //不知道工程具体有多少事件处理器的实现类,所以就需要先所有的实现类都找出来 TODO:此类的源码
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);

        if (beans != null) {
            for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
                //各个事件处理器关心的事件类型映射
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();

                for (EventType type : eventTypes) {
                    if (!config.containsKey(type)) {
                        //事件还未注册,则添加进来
                        config.put(type, new ArrayList<>());
                    }
                    //事件已被注册
                    config.get(type).add(entry.getValue());
                }
            }
        }

        //启动线程
        Thread thread = new Thread(() -> {
            while (true) {
                //获取消息队列的键
                String key = RedisKeyUtil.getEventQueueKey();
                //一直在找队列中是否有事件,无事件则一直阻塞
                List<String> events = jedisAdapter.brpop(0, key);
                for (String message : events) {
                    if (message.equals(key)) {
                        continue;
                    }

                    //取出事件时,将指定的Json反序列化为指定类的对象
                    EventModel eventModel = JSON.parseObject(message, EventModel.class);

                    if (!config.containsKey(eventModel.getType())) {
                        //未注册过此类事件,即应为非法事件
                        logger.error("不能识别的事件");
                        continue;
                    }

                    //config识别出应该由何种handler来处理该类事件,所有要处理的handler链
                    for (EventHandler handler : config.get(eventModel.getType())) {
                        handler.doHandle(eventModel);
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
