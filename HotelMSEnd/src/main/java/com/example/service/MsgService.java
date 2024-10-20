package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Circulars;
import com.example.mapper.MsgMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 日期：2024/3/13 1:05
 */
@Service
public class MsgService {
    @Resource
    private MsgMapper msgMapper;

    public PageInfo<Circulars> selectByUserId(Circulars msg , Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Circulars> list = msgMapper.selectByUserId(msg);
        return PageInfo.of(list);
    }

    public void add(Circulars msg) {
        msg.setTime(DateUtil.now());
        msgMapper.add(msg);
    }

    public void deleteById(Integer id) {
        msgMapper.deleteById(id);
    }

    public Circulars selectById(Integer id) {
        return msgMapper.selectById(id);
    }

    public void updateById(Circulars msg) {
        msg.setTime(DateUtil.now());
        msgMapper.updateById(msg);
    }

    public  Integer getUnreadCount(Integer id) {
        return msgMapper.getUnreadCount(id);
    }

    public void setChecked(Integer id) {
        msgMapper.setChecked(id);
    }

    public PageInfo<Circulars> selectPage(Circulars msg, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Circulars> list = msgMapper.selectPage(msg);
        return PageInfo.of(list);
    }

    public List<Circulars> selectAll(Circulars msg) {
        return msgMapper.selectAll(msg);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids){
            msgMapper.deleteById(id);
        }
    }
}