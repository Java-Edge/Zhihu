package com.nowcoder.async;

/**
 *
 * @author Shusheng Shi
 * @since 2017/8/11 20:28
 */
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3),
    FOLLOW(4),
    UNFOLLOW(5);

    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
