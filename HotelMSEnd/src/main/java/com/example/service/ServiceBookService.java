package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Account;
import com.example.entity.ServiceBook;
import com.example.mapper.ServiceBookMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 预约服务处理
 **/
@Service
public class ServiceBookService {

    @Resource
    private ServiceBookMapper serviceBookMapper;

    /**
     * 新增
     */
    public void add(ServiceBook notice) {
        //notice.setTime(DateUtil.today());
        Account currentUser = TokenUtils.getCurrentUser();
        notice.setUser(currentUser.getId());
        //还需要加入酒店信息
        serviceBookMapper.insert(notice);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        serviceBookMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            serviceBookMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(ServiceBook notice) {
        serviceBookMapper.updateById(notice);
    }

    /**
     * 根据ID查询
     */
    public ServiceBook selectById(Integer id) {
        return serviceBookMapper.selectById(id);
    }





    /**
     * 根据user查询
     */
    public List<ServiceBook> selectByUser(Integer userid){return serviceBookMapper.selectByUser(userid);}

    /**
     * 根据hotel查询
     */
    public List<ServiceBook> selectByHotel(Integer hotel_id){return serviceBookMapper.selectByHotel(hotel_id);}

    /**
     * 查询所有
     */
    public List<ServiceBook> selectAll(ServiceBook notice) {
        return serviceBookMapper.selectAll(notice);
    }

    /**
     * 分页查询
     */
    public PageInfo<ServiceBook> selectPage(ServiceBook notice, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ServiceBook> list = serviceBookMapper.selectAll(notice);
        return PageInfo.of(list);
    }

}