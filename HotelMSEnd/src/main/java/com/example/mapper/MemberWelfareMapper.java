package com.example.mapper;

import com.example.entity.MemberWelfare;

import java.util.List;

public interface MemberWelfareMapper {
    void insert(MemberWelfare memberWelfare);

    void update(MemberWelfare memberWelfare);

    void delete(Integer id);

    List<MemberWelfare> selectByUserId(Integer userId);

    List<MemberWelfare> selectByMemberId(Integer memberId);

    MemberWelfare selectByMidAndUid(Integer memberId, Integer userId);
}
