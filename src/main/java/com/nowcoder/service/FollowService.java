package com.nowcoder.service;

import com.nowcoder.util.JedisAdapter;
import com.nowcoder.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Shusheng Shi
 * @since 2017/8/13 18:38
 */
@Service
public class FollowService {
    @Autowired
    private JedisAdapter jedisAdapter;

    /**
     * 关注一个实体(问题或者用户)
     *
     * @param userId 关注的发起者
     * @param entityId
     * @param entityType
     * @return
     */
    public boolean follow(int userId, int entityId, int entityType) {
        //关注对象放入关注列表
        String followerKey = RedisKeyUtil.getFollowerKey(entityId, entityType);

        //自己放入实体的粉丝列表
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);

        //关注时间
        Date date = new Date();

        //获取事务
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedisAdapter.multi(jedis);

        tx.zadd(followerKey, date.getTime(), String.valueOf(userId));
        tx.zadd(followeeKey, date.getTime(), String.valueOf(entityId));

        //执行事务
        List<Object> ret = jedisAdapter.exec(tx, jedis);
        return ret.size() == 2 && (Long) ret.get(0) > 0 && (Long) ret.get(1) > 0;
    }

    /**
     * 取消关注
     *
     * @param userId
     * @param entityId
     * @param entityType
     * @return
     */
    public boolean unfollow(int userId, int entityId, int entityType) {
        //获取关注列表key
        String followerKey = RedisKeyUtil.getFollowerKey(entityId, entityType);

        //获取粉丝列表key
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);

        //获取事务
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedisAdapter.multi(jedis);

        //从列表删除
        tx.zrem(followerKey, String.valueOf(userId));
        tx.zrem(followeeKey, String.valueOf(entityId));

        //执行事务
        List<Object> ret = jedisAdapter.exec(tx, jedis);
        return ret.size() == 2 && (Long) ret.get(0) > 0 && (Long) ret.get(1) > 0;
    }

    /**
     * 辅助方法,将set转为list
     *
     * @param idset 需要转化的set集合
     * @return 转化后的list列表
     */
    private List<Integer> getIdsFromSet(Set<String> idset) {
        List<Integer> ids = new ArrayList<>();
        for (String str : idset) {
            ids.add(Integer.parseInt(str));
        }
        return ids;
    }

    /**
     * 获取所有关注的用户
     *
     * @param entityType
     * @param entityId
     * @param count
     * @return
     */
    public List<Integer> getFollowers(int entityType, int entityId, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return getIdsFromSet(jedisAdapter.zrevrange(followerKey, 0, count));
    }

    //分页用
    public List<Integer> getFollowers(int entityId, int entityType, int offset, int count) {
        String folowerKey = RedisKeyUtil.getFollowerKey(entityId, entityType);
        return getIdsFromSet(jedisAdapter.zrevrange(folowerKey, offset, count));
    }

    /**
     * 获取所有关注者(粉丝)
     *
     * @param userId
     * @param entityType
     * @param count
     * @return
     */
    public List<Integer> getFollowees(int userId, int entityType, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey, 0, count));
    }

    //分页用
    public List<Integer> getFollowees(int userId, int entityType, int offset, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey, offset, offset+count));
    }

    /**
     * 关注数
     *
     * @param entityId
     * @param entityType
     * @return
     */
    public long getFollowerCount(int entityId, int entityType) {
        String folowerKey = RedisKeyUtil.getFollowerKey(entityId, entityType);
        return jedisAdapter.zcard(folowerKey);
    }

    /**
     * 粉丝数
     *
     * @param userId
     * @param entityType
     * @return
     */
    public long getFolloweeCount(int userId, int entityType) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return jedisAdapter.zcard(followeeKey);
    }

    public boolean isFollower(int userId, int entityId, int entityType) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityId, entityType);
        return jedisAdapter.zscore(followerKey, String.valueOf(userId)) != null;
    }
}
