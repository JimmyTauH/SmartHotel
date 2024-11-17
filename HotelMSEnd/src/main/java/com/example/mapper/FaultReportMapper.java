package com.example.mapper;

import com.example.entity.FaultReport;
import java.util.List;


public interface FaultReportMapper {
    // 插入故障报备记录，返回插入的记录的 ID（MySQL 会自动生成 ID）
    void insert(FaultReport faultReport);

    // 查询所有故障报备记录
    List<FaultReport> selectAll(FaultReport faultReport);

    // 根据ID查询故障报备记录
    FaultReport selectById(Integer id);

    // 根据ID删除故障报备记录
    void deleteById(Integer id);
}


