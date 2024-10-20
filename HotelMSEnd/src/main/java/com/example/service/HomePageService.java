package com.example.service;

import com.example.common.Result;
import com.example.entity.HomePage;
import com.example.mapper.HomePageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 功能：
 * 日期：2024/4/3 17:00
 */
@Service
public class HomePageService {

    @Resource
    private HomePageMapper homePageMapper;
    public Result add(HomePage homePage) {
        homePageMapper.add(homePage);
        return Result.success();
    }

    public Result update(HomePage homePage) {
        homePageMapper.update(homePage);
        return Result.success();
    }

    public Result selectById(Integer userId) {
        return Result.success(homePageMapper.selectById(userId));
    }
}