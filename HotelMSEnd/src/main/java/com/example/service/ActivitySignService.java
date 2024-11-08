package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Account;
import com.example.entity.ActivitySign;
import com.example.exception.CustomException;
import com.example.mapper.ActivitySignMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 日期：2024/2/27 20:51
 */
@Service
public class ActivitySignService {

    @Resource
    private ActivitySignMapper activitySignMapper;

    public void insert(ActivitySign activitySign) {
        //查询竞赛是否已报名
        Account currentUser = TokenUtils.getCurrentUser();
        ActivitySign dbActivitySign = this.selectByActivityIdAndUserId(activitySign.getActivityId(), currentUser.getId());
        if (dbActivitySign != null) {
            throw new CustomException(ResultCodeEnum.ACTIVITY_SIGN_ERROR);
        }
        activitySign.setUserId(currentUser.getId());
        //设置报名时间
        if (StringUtil.isEmpty(activitySign.getTime())) {
            activitySign.setTime(DateUtil.now());
        }
        activitySignMapper.insert(activitySign);
    }



    public ActivitySign selectByActivityIdAndUserId(Integer actId, Integer userId) {
        return activitySignMapper.selectByActivityIdAndUserId(actId, userId);
    }

    public PageInfo<ActivitySign> selectPage(ActivitySign activitySign, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ActivitySign> list = activitySignMapper.selectAll(activitySign);
        return PageInfo.of(list);
    }

    public Integer selectActIdByUserId(Integer id){
        return activitySignMapper.selectActIdByUserId(id);
    }


    public void deleteById(Integer id) {
        activitySignMapper.deleteById(id);
    }
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }
    public void userDelete(Integer activityId, Integer useId) {
        activitySignMapper.userDelete(activityId, useId);
    }
}