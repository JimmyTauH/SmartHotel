package com.example.entity;

import java.math.BigDecimal;

/**
 * 功能：签到实体类
 * 日期：2024/3/7 17:44
 */
public class SignIn {
    //记录id
    private Integer id;
    //用户id
    private Integer userId;
    //签到时间
    private String time;
    //本次签到的积分
    private BigDecimal integral;

    //一共签到多少次
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getIntegral() {
        return integral;
    }

    public void setIntegral(BigDecimal integral) {
        this.integral = integral;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "SignIn{" +
                "id=" + id +
                ", userId=" + userId +
                ", time='" + time + '\'' +
                ", integral='" + integral + '\'' +
                '}';
    }
}