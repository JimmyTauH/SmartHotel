package com.example.controller;

import com.example.common.Result;
import com.example.entity.Category;
import com.example.service.CategoryService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：帖子分类前端操作接口
 * 日期：2024/1/24 20:23
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    public CategoryService categoryService;

    @PostMapping("/add")
    public Result add(@RequestBody Category category){
        categoryService.add(category);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Category category){
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        categoryService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        categoryService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Category category = categoryService.selectById(id);
        return Result.success(category);
    }

    @GetMapping("/selectAll")
    public Result selectAll(Category category){
        List<Category> categories = categoryService.selectAll(category);
        return Result.success(categories);
    }

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              Category category){
        PageInfo<Category> pageInfo = categoryService.selectPage(pageNum, pageSize, category);
        return Result.success(pageInfo);
    }
}