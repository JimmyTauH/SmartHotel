package com.example.mapper;

import com.example.entity.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RoomMapper {

    /**
     * 根据状态查找房间
     * @param state 房间状态
     */
    @Select("SELECT * FROM room WHERE state = #{state}")
    List<Room> findRoomsByState(@Param("state") int state);

    /**
     * 更新房间状态
     * @param roomId 房间ID
     * @param state 要设置的状态
     */
    @Update("UPDATE room SET state = #{state} WHERE id = #{roomId}")
    void updateRoomStatus(@Param("roomId") Integer roomId, @Param("state") int state);
}
