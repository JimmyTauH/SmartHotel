package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Account;
import com.example.entity.Repair;
import com.example.entity.ServiceBook;
import com.example.mapper.RepairMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 设备维修处理
 **/
@Service
public class RepairService {

    @Resource
    private RepairMapper repairMapper;

    /**
     * 新增
     */
    public void add(Repair repair){
        Account currentUser = TokenUtils.getCurrentUser();
        Integer user_id = currentUser.getId();
        repair.setUser(user_id);
        repair.setTime(DateUtil.today());

        repairMapper.insert(repair);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        repairMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            repairMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Repair repair) {
        repairMapper.updateById(repair);
    }

    /**
     * 修改状态
     */
    public void updateState(Integer id) {
        Repair repair = repairMapper.selectById(id);
        repair.setState(true);
        repairMapper.updateById(repair);
    }


    /**
     * 根据ID查询
     */
    public Repair selectById(Integer id) {
        return repairMapper.selectById(id);
    }





    /**
     * 根据user查询
     */
    public List<Repair> selectByUser(Integer userid){return repairMapper.selectByUser(userid);}

    /**
     * 根据hotel查询
     */
    public List<Repair> selectByHotel(Integer hotel_id){return repairMapper.selectByHotel(hotel_id);}

    /**
     * 查询所有
     */
    public List<Repair> selectAll(Repair repair) {
        return repairMapper.selectAll(repair);
    }

    /**
     * 分页查询
     */
    public PageInfo<Repair> selectPage(Repair repair, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Repair> list = repairMapper.selectAll(repair);
        return PageInfo.of(list);
    }

}