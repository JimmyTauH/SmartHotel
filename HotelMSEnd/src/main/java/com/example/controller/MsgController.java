package com.example.controller;

import com.example.common.Result;
import com.example.entity.Circulars;
import com.example.service.MsgService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 日期：2024/3/13 1:03
 */
@RestController
@RequestMapping("/msg")
public class MsgController {
    @Resource
    private MsgService msgService;

    @PostMapping("/add")
    public Result add(@RequestBody Circulars msg){
        msgService.add(msg);
        return Result.success();
    }

    @GetMapping("/selectByUserId")
    public Result selectByUserId(Circulars msg,
                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Circulars> page = msgService.selectByUserId(msg,pageNum,pageSize);
        return Result.success(page);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        msgService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        msgService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Circulars msg = msgService.selectById(id);
        return Result.success(msg);
    }

    @PutMapping("/update")
    public Result updateById(@RequestBody Circulars msg){
        msgService.updateById(msg);
        return Result.success();
    }

    @GetMapping("/getUnreadCount/{id}")
    public Integer getUnreadCount(@PathVariable Integer id){
        return msgService.getUnreadCount(id);
    }

    @PutMapping("/setChecked/{id}")
    public void setChecked(@PathVariable Integer id){
        msgService.setChecked(id);
    }

    @GetMapping("/selectPage")
    public Result selectPage(Circulars msg,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Circulars> page = msgService.selectPage(msg,pageNum,pageSize);
        return Result.success(page);
    }

    @GetMapping("/selectAll")
    public Result selectAll(Circulars msg){
        List<Circulars> msgAll = msgService.selectAll(msg);
        return Result.success(msgAll);
    }
}