package com.example.mapper;

import com.example.entity.ServiceBook;

import java.util.List;

/**
 * 操作notice相关数据接口
*/
public interface ServiceBookMapper {

    /**
      * 新增
    */
    int insert(ServiceBook notice);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(ServiceBook notice);


    //int updateState(Integer id);

    /**
      * 根据ID查询
    */
    ServiceBook selectById(Integer id);


    /**
     * 根据user查询
     */
    List<ServiceBook> selectByUser(Integer userid);

    /**
     * 根据hotel查询
     */
    List<ServiceBook>  selectByHotel(Integer hotel_id);


    /**
      * 查询所有
    */
    List<ServiceBook> selectAll(ServiceBook notice);

}