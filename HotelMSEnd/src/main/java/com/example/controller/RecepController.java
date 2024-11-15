package com.example.controller;

import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.dto.CheckInRequest;
import com.example.entity.Recep;
import com.example.entity.Room;
import com.example.service.RecepService;
import com.example.service.RoomService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 管理员前端操作接口
 **/
@RestController
@RequestMapping("/recep")
public class RecepController {

    @Resource
    private RecepService recepService;

    @Resource
    private RoomService roomService; // 处理房间操作的服务

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Recep recep) {
        recepService.add(recep);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        recepService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        recepService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Recep recep) {
        recepService.updateById(recep);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Recep recep = recepService.selectById(id);
        return Result.success(recep);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Recep recep ) {
        List<Recep> list = recepService.selectAll(recep);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Recep recep,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Recep> page = recepService.selectPage(recep, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 获取可用房间列表
     */
    @GetMapping("/available-room")
    public Result getAvailableRooms() {
        List<Room> availableRooms = roomService.getAvailableRooms();
        return Result.success(availableRooms);
    }

    @PostMapping("/checkin")
    public Result handleCheckIn(@RequestBody Map<String, Object> request) {
        try {
            // 获取并转换 room 字段为整数
            String roomStr = (String) request.get("room");
            Integer roomId;
            try {
                roomId = Integer.parseInt(roomStr); // 转换 room 字符串为整数
            } catch (NumberFormatException e) {
                return Result.error(ResultCodeEnum.PARAM_ERROR); // 如果转换失败，返回参数错误
            }

            // 提取 guests 数据并转换为 List<Guest>
            List<Map<String, String>> guestsMap = (List<Map<String, String>>) request.get("guests");
            List<CheckInRequest.Guest> guests = guestsMap.stream().map(guestMap -> {
                CheckInRequest.Guest guest = new CheckInRequest.Guest();
                guest.setName(guestMap.get("name"));
                guest.setGender(guestMap.get("gender"));
                guest.setIdCard(guestMap.get("idCard"));
                guest.setPhone(guestMap.get("phone"));
                return guest;
            }).collect(Collectors.toList()); // 使用 Collectors.toList() 收集结果

            // 构造 CheckInRequest DTO
            CheckInRequest checkInRequest = new CheckInRequest();
            checkInRequest.setRoomId(roomId);
            checkInRequest.setGuests(guests);

            // 调用 service 处理入住逻辑
            recepService.checkIn(checkInRequest);
            return Result.success("办理入住成功！");
        } catch (Exception e) {
            return Result.error(ResultCodeEnum.CHECKIN_FAILURE);
        }
    }
}