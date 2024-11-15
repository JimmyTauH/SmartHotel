package com.example.service;

import com.example.entity.Room;
import com.example.mapper.RoomMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoomService {

    @Resource
    private RoomMapper roomMapper;

    public static final int STATE_AVAILABLE = 0;     // 空闲
    public static final int STATE_RESERVED = 1;      // 已预定
    public static final int STATE_OCCUPIED = 2;      // 已入住
    public static final int STATE_CLEANING = 3;      // 打扫整理中

    /**
     * 获取所有可用房间
     */
    public List<Room> getAvailableRooms() {
        return roomMapper.findRoomsByState(STATE_AVAILABLE); // 使用状态值查询可用房间
    }

    /**
     * 更新房间状态
     */
    public void updateRoomStatus(Integer roomId, int state) {
        roomMapper.updateRoomStatus(roomId, state); // 更新房间状态
    }
}
