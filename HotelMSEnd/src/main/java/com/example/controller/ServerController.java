package com.example.controller;

import com.example.common.Result;
import com.example.entity.Server;
import com.example.service.ServerService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：用户控制器
 * 日期：2024/1/16 16:31
 */
@RestController
@RequestMapping("/server")
public class ServerController {

    @Resource
    private ServerService serverService;

    //添加用户
    @PostMapping("/add")
    public Result add(@RequestBody Server server) {
        serverService.add(server);
        return Result.success();
    }

    //根据id查询用户
    @GetMapping("/select/{id}")
    public Result selectById(@PathVariable("id") Integer id) {
        return Result.success(serverService.selectById(id));
    }

    //批量查询用户
    @GetMapping("/select/batch")
    public Result selectBatch(Server server) {
        List<Server> servers = serverService.selectBatch(server);
        return Result.success(servers);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable("id") Integer id) {
        serverService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        serverService.deleteBatch(ids);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Server server) {
        serverService.update(server);
        return Result.success();
    }

    @GetMapping("/selectPage")
    public Result selectPage(Server server,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
       PageInfo<Server> pageInfo = serverService.selectPage(server, pageNum, pageSize);
       return Result.success(pageInfo);
    }

    //更新积分
    @PutMapping("/updateIntegral")
    public Result updateIntegral(@RequestBody Server server) {
        serverService.updateIntegral(server);
        return Result.success();
    }
    @PutMapping("/updateIntegral2")
    public Result updateIntegral2(@RequestBody Server server) {
        serverService.updateIntegral2(server);
        return Result.success();
    }

}