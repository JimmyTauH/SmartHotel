package com.example.service;

import com.example.common.enums.LikesModuleEnum;
import com.example.entity.*;
import com.example.mapper.FaultReportMapper; // 引入 Mapper
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FaultReportService {

    @Resource
    private FaultReportMapper faultReportMapper;

    @Resource
    private UserService userService;

    // 创建新的故障报告
    public void createFaultReport(FaultReport faultReport) {
        faultReportMapper.insert(faultReport);
    }

    // 获取所有故障报告
    public List<FaultReport> selectAll(FaultReport faultReport) {
        return faultReportMapper.selectAll(faultReport);
    }

    // 根据 ID 获取故障报告
    public FaultReport selectById(Integer Id) {
        FaultReport faultReport = faultReportMapper.selectById(Id);
        User user = userService.selectById(faultReport.getLogisticsId());
//        faultReport.setUser(user); //设置用户信息
        return faultReport;
    }

    // 删除故障报告
    public void deleteById(Integer id) {
        faultReportMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            deleteById(id);
        }
    }

    public PageInfo<FaultReport> selectPage(FaultReport faultReport, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<FaultReport> pageList = faultReportMapper.selectAll(faultReport);
        return PageInfo.of(pageList);
    }
}

