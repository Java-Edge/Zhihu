package com.nowcoder.async.handler;

import com.nowcoder.async.EventHandler;
import com.nowcoder.async.EventModel;
import com.nowcoder.async.EventType;
import com.nowcoder.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shusheng Shi
 * @since 2017/8/12 21:31
 */
@Component
public class LoginExceptionHandler implements EventHandler{
    @Autowired
    private MailSender mailSender;


    @Override
    public void doHandle(EventModel model) {
        //判断发现这个用户登录异常
        Map<String, Object> map = new HashMap<>();
        mailSender.sendWithHTMLTemplate(model.getExt("email"), "登录ip异常", "mails/login_exception.html", map);
        map.put("username", model.getExt("username"));
    }

    @Override
    public List<EventType> getSupportEventTypes() {

        return Arrays.asList(EventType.LOGIN);
    }
}
