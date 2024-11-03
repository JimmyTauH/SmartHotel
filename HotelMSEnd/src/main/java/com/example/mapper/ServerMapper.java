package com.example.mapper;

import com.example.entity.Server;

import java.math.BigDecimal;
import java.util.List;

/**
 * 功能：用户表
 * 日期：2024/1/16 16:34
 */
public interface ServerMapper {

    void insert(Server server);

    Server selectById(Integer id);

    List<Server> selectAll(Server server);

    Server selectByUsername(String username);

    void deleteById(Integer id);

    void update(Server server);

    void updateIntegral(Server server);

    void updateIntegralByWelfare(Integer id, BigDecimal integral);
}