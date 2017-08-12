package com.nowcoder.async;

import java.util.List;

/**
 *
 * 记录handler映射
 *
 * @author Shusheng Shi
 * @since 2017/8/11 21:24
 */
public interface EventHandler {

    //处理此类事件
    void doHandle(EventModel model);

    //让别人知道自己关心哪些事件
    List<EventType> getSupportEventTypes();
}
