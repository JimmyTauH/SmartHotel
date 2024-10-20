package com.example.service;

import com.example.entity.Account;
import com.example.entity.Collect;
import com.example.mapper.CollectMapper;
import com.example.utils.TokenUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 功能：收藏服务
 * 日期：2024/2/21 22:19
 */
@Service
public class CollectService {

    @Resource
    private CollectMapper collectMapper;


    public void setCollect(Collect collect) {
        //获取当前用户
        Account currentUser = TokenUtils.getCurrentUser();
        //设置当前用户id
        collect.setUserId(currentUser.getId());
        //查询当前用户是否已收藏
        Collect dbcollect = collectMapper.selectUserCollect(collect);
        //判断是否已收藏
        if (dbcollect == null) {
            //未收藏
            collectMapper.insert(collect);
        } else {
            //已收藏
            collectMapper.deleteUserCollect(dbcollect.getId());
        }
    }

    public int selectByFidAndModule(@Param("fid") Integer fid, @Param("module") String module) {
        return collectMapper.selectByFidAndModule(fid, module);
    }

    /**
     * 查询当前用户是否已收藏
     */
    public Collect selectUserCollect(Integer blogId, String value) {
        Account currentUser = TokenUtils.getCurrentUser();
        Collect collect = new Collect();
        collect.setUserId(currentUser.getId());
        collect.setFid(blogId);
        collect.setModule(value);
        return collectMapper.selectUserCollect(collect);
    }


    public void deleteByFidAndModule(@Param("fid") Integer fid, @Param("module") String module) {
        collectMapper.deleteByFidAndModule(fid, module);
    }
}