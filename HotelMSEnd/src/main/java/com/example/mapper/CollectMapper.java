package com.example.mapper;

import com.example.entity.Collect;
import org.apache.ibatis.annotations.Param;

public interface CollectMapper {
    void insert(Collect collect);

    Collect selectUserCollect(Collect collect);

    void deleteUserCollect(Integer id);

    int selectByFidAndModule(@Param("fid") Integer fid, @Param("module") String module);

    void deleteByFidAndModule(Integer fid, String module);
}
