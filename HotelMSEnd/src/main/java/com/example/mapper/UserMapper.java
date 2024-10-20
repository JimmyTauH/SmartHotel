package com.example.mapper;

import com.example.entity.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * 功能：用户表
 * 日期：2024/1/16 16:34
 */
public interface UserMapper {

    void insert(User user);

    User selectById(Integer id);

    List<User> selectAll(User user);

    User selectByUsername(String username);

    void deleteById(Integer id);

    void update(User user);

    void updateIntegral(User user);

    void updateIntegralByWelfare(Integer id, BigDecimal integral);
}