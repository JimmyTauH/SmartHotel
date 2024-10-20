package com.example.controller;

import com.example.common.Result;
import com.example.entity.MemberWelfare;
import com.example.service.MemberWelfareService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 日期：2024/4/7 11:02
 */
@RestController
@RequestMapping("/memberWelfare")
public class MemberWelfareController {

    @Resource
    private MemberWelfareService memberWelfareService;

    @PostMapping("/add")
    public Result add(@RequestBody MemberWelfare memberWelfare) {
        memberWelfareService.add(memberWelfare);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody MemberWelfare memberWelfare) {
        memberWelfareService.update(memberWelfare);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        memberWelfareService.delete(id);
        return Result.success();
    }

    @GetMapping("/selectByUserId/{userId}")
    public Result selectByUserId(@PathVariable Integer userId) {
        List<MemberWelfare> memberWelfareList = memberWelfareService.selectByUserId(userId);
        return Result.success(memberWelfareList);
    }

    @GetMapping("/selectByMidAndUid/{memberId}/{userId}")
    public Result selectByMidAndUid(@PathVariable Integer memberId,@PathVariable Integer userId) {
        MemberWelfare memberWelfareList = memberWelfareService.selectByMidAndUid(memberId,userId);
        return Result.success(memberWelfareList);
    }
}