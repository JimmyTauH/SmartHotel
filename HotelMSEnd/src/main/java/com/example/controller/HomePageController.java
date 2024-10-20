package com.example.controller;

import com.example.common.Result;
import com.example.entity.HomePage;
import com.example.service.HomePageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 功能：个人主页接口
 * 日期：2024/4/3 16:57
 */
@RestController
@RequestMapping("/homePage")
public class HomePageController {

    @Resource
    private HomePageService homePageService;

    @PostMapping("/add")
    public Result add(@RequestBody HomePage homePage) {
        return homePageService.add(homePage);
    }

    @PutMapping("/update")
    public Result update(@RequestBody HomePage homePage) {
        return homePageService.update(homePage);
    }

    @GetMapping("/select/{userId}")
    public Result selectById(@PathVariable Integer userId) {
        return homePageService.selectById(userId);
    }
}