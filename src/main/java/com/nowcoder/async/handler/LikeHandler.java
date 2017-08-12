package com.nowcoder.async.handler;

import com.nowcoder.async.EventHandler;
import com.nowcoder.async.EventModel;
import com.nowcoder.async.EventType;
import com.nowcoder.model.Message;
import com.nowcoder.model.User;
import com.nowcoder.service.MessageService;
import com.nowcoder.service.UserService;
import com.nowcoder.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 因被点赞而发邮件
 *
 * 需实现EventHandler接口,EventConsumer才能发现
 *
 * @author Shusheng Shi
 * @since 2017/8/11 22:07
 */
@Component
public class LikeHandler implements EventHandler {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @Override
    public void doHandle(EventModel model) {
        //构造一条消息
        Message message = new Message();

        //消息由系统发送
        message.setFromId(WendaUtil.SYSTEM_USERID);

        //接受者为所赞实体的的所属者
        //message.setToId(model.getEntityOwnerId());
        //为方便测试,发给自己
        message.setToId(model.getActorId());
        message.setCreatedDate(new Date());

        //点赞用户
        User user = userService.getUser(model.getActorId());
        message.setContent("用户"+user.getName()+
                "赞了你的评论,http://127.0.0.1:8080/question/" + model.getExt("questionId"));

        messageService.addMessage(message);
    }



    @Override
    public List<EventType> getSupportEventTypes() {
        //只关心LIKE事件
        return Arrays.asList(EventType.LIKE);
    }

}
