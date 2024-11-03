package com.example.controller;

import com.example.common.Result;
import com.example.entity.Activity;
import com.example.service.ActivityService;
import com.example.service.ActivitySignService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：竞赛前端操作接口
 * 日期：2024/1/31 20:50
 */

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Resource
    private ActivityService activityService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Activity activity) {
        activityService.add(activity);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        activityService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        activityService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Activity activity) {
        activityService.updateById(activity);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Activity activity = activityService.selectById(id);
        return Result.success(activity);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Activity activity) {
        List<Activity> list = activityService.selectAll(activity);
        return Result.success(list);
    }

    /**
     * 按名字查询
     */
    @GetMapping("/selectByName/{name}")
    public Result selectByName(@PathVariable String name) {
        List<Activity> activities = activityService.selectByName(name);
        return Result.success(activities);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Activity activity,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Activity> page = activityService.selectPage(activity, pageNum, pageSize);
        return Result.success(page);
    }

    //查询用户参加的竞赛
    @GetMapping("/selectUser")
    public Result selectUser(Activity activity,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Activity> page = activityService.selectUser(activity, pageNum, pageSize);
        return Result.success(page);
    }
    //查询用户点赞的竞赛
    @GetMapping("/selectLike")
    public Result selectLike(Activity activity,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Activity> page = activityService.selectLike(activity, pageNum, pageSize);
        return Result.success(page);
    }
    //查询用户收藏的竞赛
    @GetMapping("/selectCollect")
    public Result selectCollect(Activity activity,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Activity> page = activityService.selectCollect(activity, pageNum, pageSize);
        return Result.success(page);
    }
    //查询用户评论的竞赛
    @GetMapping("/selectComment")
    public Result selectComment(Activity activity,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Activity> page = activityService.selectComment(activity, pageNum, pageSize);
        return Result.success(page);
    }

    //查询热门竞赛
    @GetMapping("/selectActivityTop")
    public Result selectActivityTop() {
        List<Activity> list = activityService.selectBlogTop();
        return Result.success(list);
    }

    //更新阅读量
    @PutMapping("/updateCount/{activityId}")
    public void updateCount(@PathVariable Integer activityId) {
        activityService.updateCount(activityId);
    }

}