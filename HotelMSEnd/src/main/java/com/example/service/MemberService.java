package com.example.service;

import com.example.entity.Account;
import com.example.entity.Member;
import com.example.mapper.MemberMapper;
import com.example.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 日期：2024/4/5 15:39
 */
@Service
public class MemberService {
    @Resource
    private MemberMapper memberMapper;


    public void add(Member member) {
        Account currentUser = TokenUtils.getCurrentUser();
        member.setUserId(currentUser.getId());
        memberMapper.add(member);
    }

    public List<Member> selectByUserId(Integer userId) {
        if (memberMapper.selectByUserId(userId) != null) {
            List<Member> memberList = memberMapper.selectByUserId(userId);
            return memberList;
        }
        return null;
    }

    public void update(Member member) {
        memberMapper.update(member);
    }

    public void delete(Integer id) {
        memberMapper.delete(id);
    }

    public Member selectByMemberId(Integer id) {
        return memberMapper.selectByMemberId(id);
    }
}