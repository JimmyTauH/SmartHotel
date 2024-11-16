package com.example.service;

import com.example.entity.CheckIn;
import com.example.entity.CheckOut;
import com.example.exception.CustomException;
import com.example.mapper.CheckInMapper;
import com.example.mapper.CheckOutMapper;
import com.example.service.RoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CheckInService {

    @Resource
    private CheckInMapper checkInMapper;

    @Resource
    private CheckOutMapper checkOutMapper;

    @Resource
    private RoomService roomService;

    /**
     * 获取指定房间的入住客人列表
     *
     * @param roomId 房间ID
     * @return 客人列表
     */
    public List<CheckIn> getGuestsByRoomId(Integer roomId) {
        return checkInMapper.findGuestsByRoomId(roomId);
    }

    /**
     * 清空房间的所有入住记录并设置房间状态为可用
     *
     * @param roomId 房间ID
     */
    public void clearRoomGuests(Integer roomId) {
        // 查询所有入住记录
        List<CheckIn> guests = checkInMapper.findGuestsByRoomId(roomId);

        if (guests.isEmpty()) {
            return;
        }

        // 将入住记录保存到退房记录表
        for (CheckIn guest : guests) {
            CheckOut checkOut = new CheckOut();
            checkOut.setRoomId(roomId);
            checkOut.setOuttime(LocalDateTime.now());
            checkOut.setGuestName(guest.getGuestName());
            checkOut.setGender(guest.getGender());
            checkOut.setGuestId(guest.getGuestId());
            checkOut.setGuestPhone(guest.getGuestPhone());
            checkOutMapper.insert(checkOut);
        }

        // 删除入住记录
        checkInMapper.deleteByRoomId(roomId);

        // 更新房间状态为可用
        roomService.updateRoomStatus(roomId, RoomService.STATE_AVAILABLE);
    }


    /**
     * 为单个客人办理退房
     *
     * @param roomId  房间ID
     * @param guestId 客人ID
     */
    /**
     * 为单个客人办理退房
     *
     * @param roomId  房间ID
     * @param guestId 客人ID
     */
    public void checkoutGuest(Integer roomId, Integer guestId) {
        // 查询客人入住记录
        System.out.println("roomId: " + roomId+";"+"guestId: " + guestId);
        CheckIn guest = checkInMapper.findGuestById(guestId);

        if (guest == null) {
            return;
        }

        // 保存到退房记录表
        CheckOut checkOut = new CheckOut();
        checkOut.setRoomId(roomId);
        checkOut.setOuttime(LocalDateTime.now());
        checkOut.setGuestName(guest.getGuestName());
        checkOut.setGender(guest.getGender());
        checkOut.setGuestId(guest.getGuestId());
        checkOut.setGuestPhone(guest.getGuestPhone());
        checkOutMapper.insert(checkOut);

        // 检查该房间是否还有其他客人
        List<CheckIn> remainingGuests = checkInMapper.findGuestsByRoomId(roomId);

        // 如果没有客人，设置房间状态为可用
        if (remainingGuests.isEmpty()) {
            roomService.updateRoomStatus(roomId, RoomService.STATE_AVAILABLE);
        }
    }

}
