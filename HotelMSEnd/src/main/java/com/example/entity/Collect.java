package com.example.entity;

/**
 * 功能：收藏实体类
 * 日期：2024/2/21 17:16
 */
public class Collect {
    private Integer id;
    private Integer fid;
    private Integer userId;
    private String module;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}