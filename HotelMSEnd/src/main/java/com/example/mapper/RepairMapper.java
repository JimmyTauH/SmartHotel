package com.example.mapper;

import com.example.entity.Repair;
import com.example.entity.ServiceBook;

import java.util.List;

/**
 * 故障报修数据接口
 */
public interface RepairMapper {

    /**
     * 新增
     */
    int insert(Repair repair);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Repair repair);


    //int updateState(Integer id);

    /**
     * 根据ID查询
     */
    Repair selectById(Integer id);


    /**
     * 根据user查询
     */
    List<Repair> selectByUser(Integer userid);

    /**
     * 根据hotel查询
     */
    List<Repair>  selectByHotel(Integer hotel_id);


    /**
     * 查询所有
     */
    List<Repair> selectAll(Repair repair);

}
