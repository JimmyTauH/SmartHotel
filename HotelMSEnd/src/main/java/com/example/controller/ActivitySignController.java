package com.example.controller;

import com.example.common.Result;
import com.example.entity.Activity;
import com.example.entity.ActivitySign;
import com.example.service.ActivitySignService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：竞赛报名接口
 * 日期：2024/2/27 20:50
 */
@RestController
@RequestMapping("activitySign")
public class ActivitySignController {
    @Resource
    private ActivitySignService activitySignService;

    @PostMapping("/add")
    public Result add(@RequestBody ActivitySign activitySign){
        activitySignService.add(activitySign);
        return Result.success();
    }

    @PostMapping("/insert")
    public Result insert(@RequestBody ActivitySign activitySign){
        activitySignService.insert(activitySign);
        return Result.success();
    }

    @PutMapping("/update")
    public Result updateById(@RequestBody ActivitySign activitySign) {
        activitySignService.updateById(activitySign);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        activitySignService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        activitySignService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(ActivitySign activitySign,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<ActivitySign> page = activitySignService.selectPage(activitySign, pageNum, pageSize);
        return Result.success(page);
    }
    /**
     * 用户删除已报名的竞赛
     */
    @DeleteMapping("/delete/user/{activityId}/{userId}")
    public Result userDelete(@PathVariable Integer activityId, @PathVariable Integer userId) {
        activitySignService.userDelete(activityId, userId);
        return Result.success();
    }
}