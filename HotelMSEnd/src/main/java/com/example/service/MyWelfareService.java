package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.*;
import com.example.exception.CustomException;
import com.example.mapper.MyWelfareMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 日期：2024/4/8 15:22
 */
@Service
public class MyWelfareService {
    @Resource
    private MyWelfareMapper myWelfareMapper;
    @Resource
    private MemberWelfareService memberWelfareService;
    @Resource
    private MemberService memberService;
    @Resource
    private UserService userService;


    @Transactional
    public void add(MyWelfare myWelfare) {
        Account currentUser = TokenUtils.getCurrentUser();
        //查询会员档位积分
        Member member = memberService.selectByMemberId(myWelfare.getMemberId());
        //查询我的积分
        User user = userService.selectById(currentUser.getId());
        //查询创作者的积分
        User dbUser = userService.selectById(myWelfare.getDbUserId());
        // 查询会员福利
        MemberWelfare memberWelfare = memberWelfareService.selectByMidAndUid(myWelfare.getMemberId(), myWelfare.getDbUserId());
        if (member.getIntegral().compareTo(user.getIntegral()) > 0) {
            throw new CustomException(ResultCodeEnum.INTEGRAL_NOT_ENOUGH);
        }
        if (member.getIntegral().compareTo(user.getIntegral()) <= 0) {
            // 设置我的福利
            if (memberWelfare != null) {
                myWelfare.setTitle(memberWelfare.getTitle());
                myWelfare.setContent(memberWelfare.getContent());
                myWelfare.setTime(DateUtil.now());
                myWelfare.setUserId(currentUser.getId());
                myWelfareMapper.insert(myWelfare);
                //扣积分，打积分
                userService.updateIntegralByWelfare(dbUser.getId(), dbUser.getIntegral().add(member.getIntegral()));
                userService.updateIntegralByWelfare(currentUser.getId(), user.getIntegral().subtract(member.getIntegral()));
                //记录流水
                RecordsService.addRecord(member.getIntegral(), "开通会员扣除", user.getId());
                RecordsService.addRecord(member.getIntegral(), "开通会员获取", dbUser.getId());
            }
        }
    }

    public void update(MyWelfare myWelfare) {
        myWelfareMapper.update(myWelfare);
    }

    public void delete(Integer id) {
        myWelfareMapper.delete(id);
    }

    public MyWelfare selectById(Integer id) {
        if (myWelfareMapper.selectById(id) != null) {
            return myWelfareMapper.selectById(id);
        }
        return null;
    }

    public PageInfo<MyWelfare> selectByUserId(MyWelfare myWelfare, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (myWelfareMapper.selectByUserId(myWelfare) != null) {
            List<MyWelfare> myWelfares = myWelfareMapper.selectByUserId(myWelfare);
            return PageInfo.of(myWelfares);
        }
        return null;
    }

    public PageInfo<MyWelfare> selectPage(MyWelfare myWelfare, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MyWelfare> myWelfareList = myWelfareMapper.selectAll(myWelfare);
        return PageInfo.of(myWelfareList);
    }

    public List<MyWelfare> selectAll(MyWelfare myWelfare) {
        if (myWelfareMapper.selectAll(myWelfare) != null) {
            return myWelfareMapper.selectAll(myWelfare);
        }
        return null;
    }

    public MyWelfare selectByMidAndDbUid(@Param("memberId") Integer memberId, @Param("dbUserId") Integer dbUserId, @Param("userId") Integer userId) {
        if (myWelfareMapper.selectByMidAndDbUid(memberId, dbUserId, userId) != null) {
            return myWelfareMapper.selectByMidAndDbUid(memberId, dbUserId, userId);
        }
        return null;
    }

    public Integer selectCount(Integer userId) {
        return myWelfareMapper.selectCount(userId);
    }
}