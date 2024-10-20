package com.example.mapper;

import com.example.entity.Member;

import java.util.List;

public interface MemberMapper {
    void add(Member member);

    List<Member> selectByUserId(Integer userId);

    void update(Member member);

    void delete(Integer id);

    Member selectByMemberId(Integer id);
}
