package com.example.controller;

import com.example.common.Result;
import com.example.entity.FaultReport;
import com.example.service.FaultReportService;
import com.example.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/report-fault")
public class FaultReportController {

    @Resource
    private FaultReportService faultReportService;

    @Resource
    private UserService userService;

    // 创建新的故障报告
    @PostMapping("/create")
    public Result createFaultReport(@RequestBody FaultReport faultReport) {
        faultReportService.createFaultReport(faultReport);
        return Result.success();
    }

    //根据ID删除
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        faultReportService.deleteById(id);
        return Result.success();
    }

    //批量删除
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        faultReportService.deleteBatch(ids);
        return Result.success();
    }

    //查询所有
    @GetMapping("/selectAll")
    public Result selectAll(FaultReport faultReport) {
        List<FaultReport> faultReports = faultReportService.selectAll(faultReport);
        return Result.success(faultReports);
    }

    //根据ID查询
    @GetMapping("/select/{id}")
    public Result selectById(@PathVariable Integer id) {
        FaultReport faultReport = faultReportService.selectById(id);
        return Result.success(faultReport);
    }

    //分页查询
    @GetMapping("/selectPage")
    public Result selectPage(FaultReport faultReport,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<FaultReport> pageInfo = faultReportService.selectPage(faultReport, pageNum, pageSize);
        return Result.success(pageInfo);
    }
}



