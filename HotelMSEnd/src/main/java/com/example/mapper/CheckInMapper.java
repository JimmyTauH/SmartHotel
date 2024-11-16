package com.example.mapper;

import com.example.entity.CheckIn;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CheckInMapper {

    @Insert("INSERT INTO check_in (room_id, intime, guest_name, gender, guest_id, guest_phone) " +
            "VALUES (#{roomId}, #{intime}, #{guestName}, #{gender}, #{guestId}, #{guestPhone})")
    void insert(CheckIn checkIn);
}

