package com.example.controller;

import com.example.common.Result;
import com.example.entity.Records;
import com.example.service.RecordsService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：收支明细前端操作接口
 * 日期：2024/4/3 20:53
 */
@RestController
@RequestMapping("/records")
public class RecordsController {
    @Resource
    private RecordsService recordsService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Records records){
        recordsService.add(records);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody Records records){
        recordsService.update(records);
        return Result.success();
    }

    /**
     * 删除ById
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        recordsService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        recordsService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 根据id查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        return Result.success(recordsService.selectById(id));
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Records records){
        return Result.success(recordsService.selectAll(records));
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Records records,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Records> pageInfo = recordsService.selectPage(records,pageNum,pageSize);
        return Result.success(pageInfo);
    }
}