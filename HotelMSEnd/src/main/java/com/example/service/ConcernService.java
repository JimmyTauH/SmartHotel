package com.example.service;

import cn.hutool.core.date.DateUtil;

import com.example.entity.Concern;
import com.example.mapper.ConcernMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 日期：2024/4/3 13:43
 */
@Service
public class ConcernService {
    @Resource
    private ConcernMapper concernMapper;
    public int add(Concern concern) {
        concern.setTime(DateUtil.now());
        return concernMapper.add(concern);
    }
    public int delete(Integer userId,  Integer creatorId) {
        return concernMapper.delete(userId, creatorId);
    }

    public int selectCount(Integer id) {
        return concernMapper.selectCount(id);
    }

    public int selectIsConcern(Integer userId,Integer creatorId) {
        return concernMapper.selectIsConcern(userId,creatorId);
    }

    public PageInfo<Concern> selectConcernList(Concern concern ,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Concern> concerns = concernMapper.selectConcernList(concern);
        return PageInfo.of(concerns);
    }
}