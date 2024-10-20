package com.example.controller;

import com.example.common.Result;
import com.example.entity.Concern;
import com.example.service.ConcernService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：关注接口
 * 日期：2024/4/3 13:41
 */
@RestController
@RequestMapping("/concern")
public class ConcernController {
    @Resource
    private ConcernService concernService;

    //关注
    @PostMapping("/add")
    public Result add(@RequestBody Concern concern) {
        concernService.add(concern);
        return Result.success();
    }
    //取消关注
    @DeleteMapping("/delete/{userId}/{creatorId}")
    public Result delete(@PathVariable Integer userId, @PathVariable Integer creatorId) {
        concernService.delete(userId, creatorId);
        return Result.success();
    }

    //查询关注数量
    @GetMapping("/selectCount/{id}")
    public Result selectCount(@PathVariable Integer id) {
        return Result.success(concernService.selectCount(id));
    }

    //查询是否关注
    @GetMapping("/selectIsConcern/{userId}/{creatorId}")
    public Result selectIsConcern(@PathVariable Integer userId, @PathVariable Integer creatorId) {
        return Result.success(concernService.selectIsConcern(userId, creatorId));
    }

    //查询已关注列表
    @GetMapping("/selectConcernList")
    public Result selectConcernList(Concern concern,
                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo <Concern> list = concernService.selectConcernList(concern,pageNum,pageSize);
        return Result.success(list);
    }

}