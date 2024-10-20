package com.example.service;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.SignIn;
import com.example.exception.CustomException;
import com.example.mapper.SignInMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 功能：
 * 日期：2024/3/7 17:47
 */
@Service
public class SignInService {

    @Resource
    private SignInMapper signInMapper;
    @Transactional
    public void add(SignIn signIn) {
        //设置签到时间
        signIn.setTime(DateUtil.date().toString());
        SignIn dbSignIn = this.selectByUserId(signIn.getUserId());
        //如果没有签到记录就立马签到
        if (ObjectUtil.isNull(dbSignIn)){
            signInMapper.insert(signIn);
            RecordsService.addRecord(signIn.getIntegral(),"签到");
            return;
        }
        //如果同一天签到了就不能再签到
        Date date1 = DateUtil.parse(dbSignIn.getTime());
        Date date2 = DateUtil.parse(DateUtil.date().toString());
        long betweenDay = DateUtil.between(date1, date2, DateUnit.DAY);
        if (betweenDay == 0){
            throw new CustomException(ResultCodeEnum.SIGNING_ERROR);
        }
        else {
            signInMapper.insert(signIn);
        }
    }

    public SignIn selectByUserId(Integer userId) {
        return signInMapper.selectByUserId(userId);
    }

    public List<SignIn> selectAll(SignIn signIn) {
        return signInMapper.selectAll(signIn);
    }

    public Integer selectCount(Integer userId) {
        return signInMapper.selectCount(userId);
    }
}