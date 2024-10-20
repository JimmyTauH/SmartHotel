package com.example.controller;

import com.example.common.Result;
import com.example.entity.MyWelfare;
import com.example.service.MyWelfareService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 日期：2024/4/8 15:19
 */
@RestController
@RequestMapping("/myWelfare")
public class MyWelfareController {

    @Resource
    private MyWelfareService myWelfareService;

    @PostMapping("/add")
    public Result add(@RequestBody MyWelfare myWelfare) {
        myWelfareService.add(myWelfare);
        return Result.success();
    }

    @PostMapping("/update")
    public Result update(@RequestBody MyWelfare myWelfare) {
        myWelfareService.update(myWelfare);
        return Result.success();
    }

    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        myWelfareService.delete(id);
        return Result.success();
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        return Result.success(myWelfareService.selectById(id));
    }

    @GetMapping("/selectByUserId")
    public Result selectByUserId(MyWelfare myWelfare,
                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<MyWelfare> myWelfares = myWelfareService.selectByUserId(myWelfare, pageNum, pageSize);
        return Result.success(myWelfares);
    }

    @GetMapping("/selectByMidAndDbUid/{memberId}/{dbUserId}/{userId}")
    public Result selectByMidAndDbUid(@PathVariable Integer memberId, @PathVariable Integer dbUserId, @PathVariable Integer userId) {
        MyWelfare myWelfare = myWelfareService.selectByMidAndDbUid(memberId, dbUserId, userId);
        return Result.success(myWelfare);
    }

    @GetMapping("/selectAll")
    public Result selectAll(MyWelfare myWelfare) {
        List<MyWelfare> myWelfares = myWelfareService.selectAll(myWelfare);
        return Result.success(myWelfares);
    }

    @GetMapping("/selectPage")
    public Result selectPage(MyWelfare myWelfare,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<MyWelfare> page = myWelfareService.selectPage(myWelfare, pageNum, pageSize);
        return Result.success(page);
    }

    @GetMapping("/selectMemberCount/{userId}")
    public Result selectMemberCount(@PathVariable Integer userId) {
        Integer count = myWelfareService.selectCount(userId);
        return Result.success(count);
    }
}