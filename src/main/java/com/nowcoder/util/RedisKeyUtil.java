package com.nowcoder.util;

/**
 * Redis的key生成类,防止重名覆盖
 *
 * @author Shusheng Shi
 * @since 2017/8/10 22:21
 */
public class RedisKeyUtil {

    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";

    //专为异步事件队列而生
    private static String BIZ_EVENTQUEUE = "EVENT_QUEUE";

    //粉丝
    private static String BIZ_FOLLOWER = "FOLLOWER";
    //关注对象
    private static String BIZ_FOLLOWEE = "FOLLOWEE";


    public static String getLikeKey(int entityType, int entityId) {
        return BIZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getDisLikeKey(int entityType, int entityId) {
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getEventQueueKey() {
        return BIZ_EVENTQUEUE;
    }

    public static String getFollowerKey(int entityId, int entityType) {
        return BIZ_FOLLOWER + SPLIT + String.valueOf(entityId) + SPLIT + String.valueOf(entityType);
    }

    //某用户关注某类实体
    public static String getFolloweeKey(int userId,int entityType) {
        return BIZ_FOLLOWEE + SPLIT + String.valueOf(userId) + SPLIT + String.valueOf(entityType);
    }

}
