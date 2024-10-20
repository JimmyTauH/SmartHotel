package com.example.mapper;

import com.example.entity.Records;

import java.util.List;

public interface RecordsMapper {
    void add(Records records);

    void update(Records records);

    void deleteById(Integer id);

    Records selectById(Integer id);

    List<Records> selectAll(Records records);
}
