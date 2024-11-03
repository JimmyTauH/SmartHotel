package com.example.mapper;

import com.example.entity.Recep;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 操作admin相关数据接口
*/
public interface RecepMapper {

    /**
      * 新增
    */
    int insert(Recep recep);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Recep Recep);

    /**
      * 根据ID查询
    */
    Recep selectById(Integer id);

    /**
      * 查询所有
    */
    List<Recep> selectAll(Recep recep);

    @Select("select * from admin_recep where username = #{username}")
    Recep selectByUsername(String username);
}