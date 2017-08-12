package com.nowcoder.async;

import java.util.HashMap;
import java.util.Map;

/**
 * 事件当时发生的现场
 *
 * @author Shusheng Shi
 * @since 2017/8/11 20:31
 */
public class EventModel {
    /**
     * eg:评论,评论者,评论类型(依赖于entityType)及其ID
     *    问题,提问者,问题类型(依赖于entityType)及其ID
     */
    //事件类型
    private EventType type;
    //触发者
    private int actorId;
    //触发者关联触发的载体
    private int entityType;
    //载体ID
    private int entityId;

    //载体的所属者
    private int entityOwnerId;

    //
    public EventModel() {

    }

    //构造函数
    public EventModel(EventType type) {
        this.type = type;
    }
    //扩展字段
    private Map<String, String> exts = new HashMap<>();

    public EventModel setExt(String key, String value) {
        exts.put(key, value);
        //为方便以后的调用:eg,xx.setType().setXX().setYY()  链式调用
        return this;
    }

    public String getExt(String key) {
        return exts.get(key);
    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
}
