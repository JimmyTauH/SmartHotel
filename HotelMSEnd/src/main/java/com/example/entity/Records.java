package com.example.entity;

import java.math.BigDecimal;

/**
 * 功能：
 * 日期：2024/4/3 20:50
 */
public class Records {
    /**
     * id
     */
    private Integer id;

    /**
     * 积分
     */
    private BigDecimal integral;
    /**
     * 用户
     */
    private Integer userId;
    /**
     * 时间
     */
    private String time;
    /**
     * 类型
     */
    private String type;

    /**
     * 用户名
     */
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getIntegral() {
        return integral;
    }

    public void setIntegral(BigDecimal money) {
        this.integral = money;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}