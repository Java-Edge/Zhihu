package com.nowcoder.controller;

import com.nowcoder.model.EntityType;
import com.nowcoder.model.HostHolder;
import com.nowcoder.service.LikeService;
import com.nowcoder.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 赞踩
 *
 * @author Shusheng Shi
 * @since 2017/8/10 21:59
 */
@Controller
public class LikeController {

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private LikeService likeService;

    /**
     * 赞
     *
     * @param commentId 评论ID
     * @return
     */
    @RequestMapping(path = {"/like"}, method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId) {
        //先判断用户是否存在
        if (hostHolder.getUser() == null) {
            //999:未登录
            return WendaUtil.getJSONString(999);
        }
        long likeCount = likeService.like(hostHolder.getUser().getId(), commentId, EntityType.ENTITY_COMMENT);
        return WendaUtil.getJSONString(0, String.valueOf(likeCount));
    }

    /**
     * 踩
     *
     * @param commentId 评论ID
     * @return
     */
    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId) {
        //先判断用户是否存在
        if (hostHolder.getUser() == null) {
            //999:未登录
            return WendaUtil.getJSONString(999);
        }
        long likeCount = likeService.disLike(hostHolder.getUser().getId(), commentId, EntityType.ENTITY_COMMENT);
        return WendaUtil.getJSONString(0, String.valueOf(likeCount));
    }


}
