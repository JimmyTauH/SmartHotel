package com.example.mapper;

import com.example.entity.CheckIn;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

@Mapper
public interface CheckInMapper {

    @Insert("INSERT INTO check_in (room_id, intime, guest_name, gender, guest_id, guest_phone) " +
            "VALUES (#{roomId}, #{intime}, #{guestName}, #{gender}, #{guestId}, #{guestPhone})")
    void insert(CheckIn checkIn);

    @Select("SELECT ci.* " +
            "FROM check_in ci " +
            "LEFT JOIN check_out co " +
            "ON ci.id = co.guest_id " +
            "AND ci.room_id = co.room_id " +
            "AND co.outtime >= ci.intime " +
            "WHERE ci.room_id = #{roomId} " +
            "AND (co.id IS NULL OR co.outtime < ci.intime);")
    List<CheckIn> findGuestsByRoomId(@Param("roomId") Integer roomId);

    @Delete("DELETE FROM check_in WHERE room_id = #{roomId}")
    void deleteByRoomId(@Param("roomId") Integer roomId);

    @Delete("DELETE FROM check_in WHERE id = #{guestId}")
    void deleteByGuestId(@Param("guestId") Integer guestId);

    @Select("SELECT * FROM check_in WHERE id = #{guestId}")
    CheckIn findGuestById(@Param("guestId") Integer guestId);
}

