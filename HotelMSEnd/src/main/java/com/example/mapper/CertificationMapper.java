package com.example.mapper;

import com.example.entity.Certification;

import java.util.List;

/**
 * 功能：
 * 日期：2023/11/30 21:41
 */
public interface CertificationMapper {

    void add(Certification certification);

    void update(Certification certification);

    void deleteById(Integer id);

    Certification selectById(Integer id);

    List<Certification> selectAll(Certification certification);

    Certification selectByUserId(Integer userId);

    void deleteByUserId(Integer userId);
}