package com.example.service;

import com.example.entity.Category;
import com.example.mapper.CategoryMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：帖子分类业务处理
 * 日期：2024/1/24 20:34
 */
@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    public void add(Category category) {
        categoryMapper.insert(category);
    }

    public void update(Category category) {
        categoryMapper.updateById(category);
    }

    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }

    public Category selectById(Integer id) {
        return categoryMapper.selectById(id);
    }

    public List<Category> selectAll(Category category) {
        return categoryMapper.selectAll(category);
    }

    public PageInfo<Category> selectPage(Integer pageNum, Integer pageSize, Category category) {
        PageHelper.startPage(pageNum, pageSize);
        List<Category> categories = categoryMapper.selectAll(category);
        return PageInfo.of(categories);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }
}