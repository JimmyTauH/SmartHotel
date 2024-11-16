package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.dto.CheckInRequest;
import com.example.entity.CheckIn;
import com.example.mapper.CheckInMapper;
import com.example.mapper.MemberWelfareMapper;
import com.example.service.RoomService;
import com.example.entity.Account;
import com.example.entity.Recep;
import com.example.exception.CustomException;
import com.example.mapper.RecepMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 前台业务处理
 **/
@Service
public class RecepService {

    @Resource
    private RecepMapper recepMapper;

    @Resource
    private RoomService roomService; // 注入 RoomService

    @Resource
    private CheckInMapper checkInMapper;

    /**
     * 新增
     */
    public void add(Recep recep) {
        Recep dbRecep = recepMapper.selectByUsername(recep.getUsername());
        if (ObjectUtil.isNotNull(dbRecep)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(recep.getPassword())) {
            recep.setPassword(Constants.USER_DEFAULT_PASSWORD);
        }
        if (ObjectUtil.isEmpty(recep.getName())) {
            recep.setName(recep.getUsername());
        }
        recep.setRole(RoleEnum.RECEP.name());
        recepMapper.insert(recep);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        recepMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            recepMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Recep recep) {
        recepMapper.updateById(recep);
    }

    /**
     * 根据ID查询
     */
    public Recep selectById(Integer id) {
        return recepMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Recep> selectAll(Recep recep) {
        return recepMapper.selectAll(recep);
    }

    /**
     * 分页查询
     */
    public PageInfo<Recep> selectPage(Recep recep, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Recep> list = recepMapper.selectAll(recep);
        return PageInfo.of(list);
    }

    /**
     * 登录
     */
    public Account login(Account account) {
        Account dbRecep = recepMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbRecep)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbRecep.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 生成token
        String tokenData = dbRecep.getId() + "-" + RoleEnum.RECEP.name();
        String token = TokenUtils.createToken(tokenData, dbRecep.getPassword());
        dbRecep.setToken(token);
        return dbRecep;
    }

    /**
     * 注册
     */
    public void register(Account account) {
        Recep recep = new Recep();
        BeanUtils.copyProperties(account, recep);
        add(recep);
    }

    /**
     * 修改密码
     */
    public void updatePassword(Account account) {
        Recep dbAdmin = recepMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbAdmin)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbAdmin.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        dbAdmin.setPassword(account.getNewPassword());
        recepMapper.updateById(dbAdmin);
    }

    public void checkIn(CheckInRequest checkInRequest) {
        Integer roomId = checkInRequest.getRoomId();
        List<CheckInRequest.Guest> guests = checkInRequest.getGuests();

        // 保存入住信息到 CheckIn 表
        saveGuestInfo(roomId, guests);

        // 更新房间状态为已入住
        roomService.updateRoomStatus(roomId, RoomService.STATE_OCCUPIED);
    }

    private void saveGuestInfo(Integer roomId, List<CheckInRequest.Guest> guests) {
        // 获取当前时间作为入住时间
        LocalDateTime now = LocalDateTime.now();

        for (CheckInRequest.Guest guest : guests) {
            // 创建 CheckIn 实例
            CheckIn checkIn = new CheckIn();
            checkIn.setRoomId(roomId);
            checkIn.setIntime(now);
            checkIn.setGuestName(guest.getName());
            checkIn.setGender(guest.getGender());
            checkIn.setGuestId(guest.getIdCard());
            checkIn.setGuestPhone(guest.getPhone());

            // 保存到数据库
            checkInMapper.insert(checkIn);
        }
    }
}