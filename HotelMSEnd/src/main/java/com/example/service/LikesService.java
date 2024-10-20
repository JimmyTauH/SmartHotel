package com.example.service;

import com.example.entity.Account;
import com.example.entity.Likes;
import com.example.mapper.LikesMapper;
import com.example.utils.TokenUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 功能：点赞
 * 日期：2024/2/21 17:18
 */
@Service
public class LikesService {

    @Resource
    LikesMapper likesMapper;

    public void set(Likes likes) {
        Account currentUser = TokenUtils.getCurrentUser();
        likes.setUserId(currentUser.getId());
        Likes dblLikes = likesMapper.selectUserLikes(likes);
        if (dblLikes == null) {
            likesMapper.insert(likes);
        } else {
            likesMapper.deleteById(dblLikes.getId());
        }
    }

    /**
     * 查询当前用户是否点过赞
     */
    public Likes selectUserLikes(Integer fid, String module) {
        Account currentUser = TokenUtils.getCurrentUser();
        Likes likes = new Likes();
        likes.setUserId(currentUser.getId());
        likes.setFid(fid);
        likes.setModule(module);
        return likesMapper.selectUserLikes(likes);
    }

    public int selectByFidAndModule(@Param("fid") Integer fid, @Param("module") String module) {
        return likesMapper.selectByFidAndModule(fid, module);
    }

    public void deleteByFidAndModule(@Param("fid") Integer fid, @Param("module") String module) {
        likesMapper.deleteByFidAndModule(fid, module);
    }
}
