package com.example.mapper;

import com.example.entity.CheckOut;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CheckOutMapper {

    @Insert("INSERT INTO check_out (room_id, outtime, guest_name, gender, guest_id, guest_phone) " +
            "VALUES (#{roomId}, #{outtime}, #{guestName}, #{gender}, #{guestId}, #{guestPhone})")
    void insert(CheckOut checkOut);
}
