package com.nowcoder.service;

import com.nowcoder.dao.FeedDAO;
import com.nowcoder.model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 读取feed
 *
 * @author Shusheng Shi
 * @since 2017/8/14 18:05
 */
@Service
public class FeedService {
    @Autowired
    private FeedDAO feedDAO;

    /**
     * 拉模式
     *
     * @param maxId
     * @param userIds
     * @param count
     * @return
     */
    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count) {
        return feedDAO.selectUserFeeds(maxId, userIds, count);
    }

    public boolean addFeed(Feed feed) {
        feedDAO.addFeed(feed);
        return feed.getId() > 0;
    }

    /**
     * 推模式
     *
     * @param id
     * @return
     */
    public Feed getById(int id) {
        return feedDAO.getFeedById(id);
    }


}
