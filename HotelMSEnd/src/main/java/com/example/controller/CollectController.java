package com.example.controller;

import com.example.common.Result;
import com.example.entity.Blog;
import com.example.entity.Collect;
import com.example.service.CollectService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 功能：收藏实体类
 * 日期：2024/2/21 22:15
 */
@RestController
@RequestMapping("collect")
public class CollectController {

    @Resource
    private CollectService collectService;

    @PostMapping("/set/collect")
    public Result setCollect(@RequestBody Collect collect) {
        collectService.setCollect(collect);
        return Result.success();
    }

}