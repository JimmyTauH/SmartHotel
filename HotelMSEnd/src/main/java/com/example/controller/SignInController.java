package com.example.controller;

import cn.hutool.core.date.DateUtil;
import com.example.common.Result;
import com.example.entity.SignIn;
import com.example.service.SignInService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 功能：
 * 日期：2024/3/7 17:45
 */
@RestController
@RequestMapping("signIn")
public class SignInController {

    @Resource
    private SignInService signInService;

    @PostMapping("/add")
    public Result add(@RequestBody SignIn signIn) {
        signInService.add(signIn);
        return Result.success();
    }

    //查询最近30条记录
    @GetMapping("/selectAll")
    public Result selectAll(SignIn signIn) {
        List<SignIn> signInList = signInService.selectAll(signIn);
        return Result.success(signInList);
    }

    @GetMapping("/selectCount/{userId}")
    public Result selectCount(@PathVariable Integer userId) {
        Integer count = signInService.selectCount(userId);
        return Result.success(count);
    }
}