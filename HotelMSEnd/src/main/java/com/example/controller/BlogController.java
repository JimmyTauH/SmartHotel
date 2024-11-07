package com.example.controller;

import com.example.common.Result;
import com.example.entity.Activity;
import com.example.entity.Blog;
import com.example.service.BlogService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 功能：
 * 日期：2024/1/25 22:05
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private BlogService blogService;
    //添加
    @PostMapping("/add")
    public Result add(@RequestBody Blog blog) {
        blogService.add(blog);
        return Result.success();
    }
    //修改
    @PutMapping("/update")
    public Result update(@RequestBody Blog blog) {
        blogService.update(blog);
        return Result.success();
    }
    //根据ID删除
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        blogService.deleteById(id);
        return Result.success();
    }
    //批量删除
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        blogService.deleteBatch(ids);
        return Result.success();
    }
    //根据ID查询
    @GetMapping("/select/{id}")
    public Result selectById(@PathVariable Integer id) {
        Blog blog = blogService.selectById(id);
        return Result.success(blog);
    }


    @GetMapping("/selectBytitle/{name}")
    public Result selectBytitle(@PathVariable String title) {
        List<Blog> blogs = blogService.selectBytitle(title);
        return Result.success(blogs);
    }
    //查询所有
    @GetMapping("/selectAll")
    public Result selectAll(Blog blog) {
        List<Blog> blogs = blogService.selectAll(blog);
        return Result.success(blogs);
    }
    //分页查询
    @GetMapping("/selectPage")
    public Result selectPage(Blog blog,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Blog> pageInfo = blogService.selectPage(blog, pageNum, pageSize);
        System.out.println(blog.getCategoryName());
        return Result.success(pageInfo);
    }

    //查询热门帖子
    @GetMapping("/selectBlogTop")
    public Result selectBlogTop() {
        List<Blog> blogs = blogService.selectBlogTop();
        return Result.success(blogs);
    }

    //查询推荐帖子
    @GetMapping("/selectRecommend/{blogId}")
    public Result selectRecommend(@PathVariable Integer blogId) {
        Set<Blog> blogs = blogService.selectRecommend(blogId);
        return Result.success(blogs);
    }

    //更新阅读量
    @PutMapping("/updateCount/{blogId}")
    public void updateCount(@PathVariable Integer blogId) {
        blogService.updateCount(blogId);
    }

    //分页查询当前用户的帖子列表
    @GetMapping("/selectUser")
    public Result selectUserBlogPage(Blog blog,
                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Blog> pageInfo = blogService.selectUser(blog, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    //分页查询创作者用户的帖子列表
    @GetMapping("/selectCertification")
    public Result selectCertificationBlogPage(Blog blog,
                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Blog> pageInfo = blogService.selectPage(blog, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    //查询已点过赞的帖子列表
    @GetMapping("/selectLike")
    public Result selectLike(Blog blog,
                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Blog> pageInfo = blogService.selectLike(blog, pageNum, pageSize);
        return Result.success(pageInfo);
    }
    //查询已收藏过的帖子列表
    @GetMapping("/selectCollect")
    public Result selectCollect(Blog blog,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Blog> pageInfo = blogService.selectCollect(blog, pageNum, pageSize);
        return Result.success(pageInfo);
    }
    //查询已评论过的帖子列表
    @GetMapping("/selectComment")
    public Result selectComment(Blog blog,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Blog> pageInfo = blogService.selectComment(blog, pageNum, pageSize);
        return Result.success(pageInfo);
    }
}