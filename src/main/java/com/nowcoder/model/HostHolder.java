package com.nowcoder.model;

import org.springframework.stereotype.Component;

/**
 * 专门用于存放取出的用户
 *
 * @author Shusheng Shi
 * @since 2017/8/5 18:20
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();;
    }
}
