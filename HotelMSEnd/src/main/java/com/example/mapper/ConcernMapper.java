package com.example.mapper;

import com.example.entity.Concern;

import java.util.List;

public interface ConcernMapper {
    int delete(Integer userId,  Integer creatorId);

    int add(Concern concern);

    int selectCount(Integer id);

    int selectIsConcern(Integer userId, Integer creatorId);

    List<Concern> selectConcernList(Concern concern);
}
