package com.example.controller;

import com.example.common.Result;
import com.example.entity.User;
import com.example.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：用户控制器
 * 日期：2024/1/16 16:31
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    //添加用户
    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        userService.add(user);
        return Result.success();
    }

    //根据id查询用户
    @GetMapping("/select/{id}")
    public Result selectById(@PathVariable("id") Integer id) {
        return Result.success(userService.selectById(id));
    }

    //批量查询用户
    @GetMapping("/select/batch")
    public Result selectBatch(User user) {
        List<User> users = userService.selectBatch(user);
        return Result.success(users);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable("id") Integer id) {
        userService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        userService.deleteBatch(ids);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        userService.update(user);
        return Result.success();
    }

    @GetMapping("/selectPage")
    public Result selectPage(User user,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
       PageInfo<User> pageInfo = userService.selectPage(user, pageNum, pageSize);
       return Result.success(pageInfo);
    }

    //更新积分
    @PutMapping("/updateIntegral")
    public Result updateIntegral(@RequestBody User user) {
        userService.updateIntegral(user);
        return Result.success();
    }
    @PutMapping("/updateIntegral2")
    public Result updateIntegral2(@RequestBody User user) {
        userService.updateIntegral2(user);
        return Result.success();
    }

}