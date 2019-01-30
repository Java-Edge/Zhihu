package com.javaedge.async;

import java.util.List;

/**
 * Created by javaedge on 2016/7/30.
 */
public interface EventHandler {
    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();
}
