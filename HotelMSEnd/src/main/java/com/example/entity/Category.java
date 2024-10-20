package com.example.entity;

/**
 * 功能：帖子分类
 * 日期：2024/1/24 20:22
 */
public class Category {
    /** ID */
    private Integer id;
    /** 分类名称 */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}