package com.example.mapper;

import com.example.entity.Circulars;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MsgMapper {
    List<Circulars> selectByUserId(Circulars msg);

    void add(Circulars msg);

    void deleteById(Integer id);

    Circulars selectById(Integer id);

    void updateById(Circulars msg);

    @Select("select count(*) from circulars where user_id = #{id} and isChecked = 0")
    Integer getUnreadCount(Integer id);

    @Update("UPDATE circulars SET isChecked = 1 WHERE id = #{id}")
    void setChecked(Integer id);

    List<Circulars> selectPage(Circulars msg);

    List<Circulars> selectAll(Circulars msg);
}
