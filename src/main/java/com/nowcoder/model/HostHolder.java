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

    //每个线程用每个拥有自己的User对象
    /**
     * 每个线程中都有一个自己的ThreadLocalMap类对象,可以将线程自己的对象保持到其中,各管各的,线程可以正确的访问到自己的对象
     * 将一个共用的ThreadLocal静态实例作为key,将不同对象的引用保存到不同线程的ThreadLocalMap中
     * 然后在线程执行的各处通过这个静态ThreadLocal实例的get()方法取得自己线程保存的那个对象
     * 避免了将这个对象作为参数传递的麻烦
     */
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}
