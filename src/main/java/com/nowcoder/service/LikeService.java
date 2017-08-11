package com.nowcoder.service;

import com.nowcoder.util.JedisAdapter;
import com.nowcoder.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Shusheng Shi
 * @since 2017/8/10 22:00
 */
@Service
public class LikeService {
    @Autowired
    private JedisAdapter jedisAdapter;

    /**
     * 获取赞数
     *
     * @param entityId 实体ID
     * @param entityType 实体类型
     * @return 赞数
     */
    public long getLikeCount(int entityId, int entityType) {
        //产生独立的key
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        return jedisAdapter.scard(likeKey);
    }

    /**
     * 用户对某元素喜欢的状态
     *
     * @param userId 登录用户ID
     * @param entityId 实体ID
     * @param entityType 实体类型
     * @return
     */
    public int getLikeStatus(int userId, int entityId, int entityType) {
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        if (jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
            //已经喜欢的
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
        //-1:不喜欢,0:无感
        return jedisAdapter.sismember(disLikeKey, String.valueOf(userId)) ? -1 : 0;

    }

    /**
     * 赞
     *
     * @param userId 登录用户ID
     * @param entityId 实体ID
     * @param entityType 实体类型
     * @return 赞数
     */
    public long like(int userId, int entityId, int entityType) {
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        jedisAdapter.sadd(likeKey, String.valueOf(userId));

        //赞的同时,取消踩
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
        jedisAdapter.srem(disLikeKey, String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }

    /**
     * 踩
     *
     * @param userId 登录用户ID
     * @param entityId 实体ID
     * @param entityType 实体类型
     * @return
     */
    public long disLike(int userId, int entityId, int entityType) {
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
        jedisAdapter.sadd(disLikeKey, String.valueOf(userId));

        //踩的同时,取消赞
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        jedisAdapter.srem(likeKey, String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }
}
