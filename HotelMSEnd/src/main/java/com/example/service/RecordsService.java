package com.example.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.entity.Account;
import com.example.entity.Records;
import com.example.entity.User;
import com.example.mapper.RecordsMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 功能：
 * 日期：2023/12/3 21:01
 */
@Service
public class RecordsService implements InitializingBean {
    @Resource
    private RecordsMapper recordsMapper;

    /**
     * 通过实现InitializingBean接口，可以确保在bean实例化之后、任何其他初始化方法（比如setter注入）执行之前，
     * 执行afterPropertiesSet()方法。这样可以在注入完成后，对bean进行一些必要的初始化操作，确保bean的状态正确。
     * 在给定的例子中，通过实现InitializingBean接口，将recordsMapper赋值给静态变量staticRecordsMapper，
     * 以便在静态方法addRecord中使用。
     */
    private static RecordsMapper staticRecordsMapper;



    @Override
    public void afterPropertiesSet() throws Exception {
        staticRecordsMapper = recordsMapper;
    }

    public void add(Records records) {
        recordsMapper.add(records);
    }

    public void update(Records records) {
        recordsMapper.update(records);
    }
    /**
     * 添加收支记录记录的方法，静态的，可以通过类名.方法名直接调用
     * @param type
     * @param integral
     */
    public static void addRecord(BigDecimal integral, String type){
        Records records = new Records();
        Account currentUser = TokenUtils.getCurrentUser();
        records.setUserId(currentUser.getId());
        records.setTime(DateUtil.now());
        records.setIntegral(integral);
        records.setType(type);
        staticRecordsMapper.add(records);
    }

    public static void addRecord(BigDecimal integral, String type, Integer userId){
        Records records = new Records();
        records.setUserId(userId);
        records.setTime(DateUtil.now());
        records.setIntegral(integral);
        records.setType(type);
        staticRecordsMapper.add(records);
    }
    public void deleteById(Integer id) {
        recordsMapper.deleteById(id);
    }

    public Records selectById(Integer id) {
        return recordsMapper.selectById(id);
    }

    public List<Records> selectAll(Records records) {
        return recordsMapper.selectAll(records);
    }

    public PageInfo<Records> selectPage(Records records, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Records> list = recordsMapper.selectAll(records);
        return PageInfo.of(list);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids){
            recordsMapper.deleteById(id);
        }
    }


}