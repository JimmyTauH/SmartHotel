package com.example.service;

import com.example.entity.MemberWelfare;
import com.example.mapper.MemberWelfareMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 日期：2024/4/7 11:07
 */
@Service
public class MemberWelfareService {
    @Resource
    private MemberWelfareMapper memberWelfareMapper;

    public void add(MemberWelfare memberWelfare) {
        memberWelfareMapper.insert(memberWelfare);
    }

    public void update(MemberWelfare memberWelfare) {
        memberWelfareMapper.update(memberWelfare);
    }

    public void delete(Integer id) {
        memberWelfareMapper.delete(id);
    }

    public List<MemberWelfare> selectByUserId(Integer userId) {
        if (userId != null) {
            return memberWelfareMapper.selectByUserId(userId);
        }
        return null;
    }

    public List<MemberWelfare> selectByMemberId(Integer memberId) {
        if (memberId != null) {
            return memberWelfareMapper.selectByMemberId(memberId);
        }
        return null;
    }

    public MemberWelfare selectByMidAndUid(@Param("memberId") Integer memberId, @Param("userId") Integer userId) {
        if (memberId != null && userId != null) {
            return memberWelfareMapper.selectByMidAndUid(memberId, userId);
        }
        return null;
    }
}