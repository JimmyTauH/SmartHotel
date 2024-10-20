package com.example.mapper;

import com.example.entity.MyWelfare;

import java.util.List;

public interface MyWelfareMapper {
    void insert(MyWelfare myWelfare);

    void update(MyWelfare myWelfare);

    void delete(Integer id);

    MyWelfare selectById(Integer id);

    List<MyWelfare> selectByUserId(MyWelfare myWelfare);

    List<MyWelfare> selectAll(MyWelfare myWelfare);

    MyWelfare selectByMidAndDbUid(Integer memberId, Integer dbUserId, Integer userId);

    Integer selectCount(Integer userId);
}
