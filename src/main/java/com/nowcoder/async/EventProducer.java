package com.nowcoder.async;

import com.alibaba.fastjson.JSONObject;
import com.nowcoder.util.JedisAdapter;
import com.nowcoder.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 事件的入口,统一将事件发送推到队列中去
 *
 * @author Shusheng Shi
 * @since 2017/8/11 20:56
 */
@Service
public class EventProducer {

    //保存队列的一些信息
    @Autowired
    private JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel) {
        try {
            //先将指定的对象序列化为其等效的Json字符串表示形式
            String json = JSONObject.toJSONString(eventModel);
            //之后生成消息队列的键并把事件放入redis的队列中,生产者工作结束
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);

             return true;
        } catch (Exception e) {
            return false;
        }
    }




}
