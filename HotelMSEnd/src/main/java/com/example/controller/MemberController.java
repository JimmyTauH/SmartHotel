package com.example.controller;

import com.example.common.Result;
import com.example.entity.Member;
import com.example.service.MemberService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 功能：
 * 日期：2024/4/5 15:38
 */
@RequestMapping("/member")
@RestController
public class MemberController {
    @Resource
    private MemberService memberService;

    @PostMapping("/add")
    public Result add(@RequestBody Member member) {
        memberService.add(member);
        return Result.success();
    }
    //根据作者id查找
    @GetMapping("/selectById/{userId}")
    public Result selectByUserId(@PathVariable Integer userId) {
        return Result.success(memberService.selectByUserId(userId));
    }

    //根据档位id查找
    @GetMapping("/selectByMemberId/{id}")
    public Result selectByMemberId(@PathVariable Integer id) {
        return Result.success(memberService.selectByMemberId(id));
    }
    @PutMapping("/update")
    public Result update(@RequestBody Member member) {
        memberService.update(member);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        memberService.delete(id);
        return Result.success();
    }
}