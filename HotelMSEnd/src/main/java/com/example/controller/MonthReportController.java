package com.example.controller;

import com.example.dto.*;
import com.example.service.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/month_report")
public class MonthReportController {

    @Resource
    private ReportService reportService;

    @GetMapping("/activity_sign_month")
    public Map<String, Object> getWeeklyReservationCount(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {

        Map<String, Object> result = new HashMap<>();

        // 获取一周的总预定量和总预定金额
        WeeklyReportDTO weeklySummary = reportService.getWeeklyReservationSummary(startDate, endDate);
        if (weeklySummary == null) {
            weeklySummary = new WeeklyReportDTO();  // 使用默认值
        }

        // 获取每天的总预定量和总预定金额
        List<DailyReportDTO> dailySummaries = reportService.getDailyReservationSummary(startDate, endDate);
        if (dailySummaries == null) {
            dailySummaries = Collections.emptyList();
        }

        // 获取按房型统计的每日和每周的总预定量和总预定金额
        List<RoomTypeReportDTO> roomTypeSummaries = reportService.getRoomTypeSummary(startDate, endDate);
        if (roomTypeSummaries == null) {
            roomTypeSummaries = Collections.emptyList();
        }

        // 将所有结果汇总为一个 Map 以返回给前端
        result.put("weeklySummary", weeklySummary);
        result.put("dailySummaries", dailySummaries);
        result.put("roomTypeSummaries", roomTypeSummaries);

        // 使用 ObjectMapper 序列化为 JSON 测试
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResult = objectMapper.writeValueAsString(result);
            System.out.println("序列化后的 JSON 数据:" + jsonResult);  // 打印序列化后的 JSON 数据
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("序列化失败：" + e.getMessage());
        }
        return result;
    }

    @GetMapping("/checkinout_month")
    public Map<String, Object> getWeeklyCheckinCheckoutCount(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {

        Map<String, Object> result = new HashMap<>();

        // 获取一周的总入住量
        WeeklyCheckinCheckoutDTO weeklySummary = reportService.getWeeklyCheckinCheckoutSummary(startDate, endDate);
        if (weeklySummary == null) {
            weeklySummary = new WeeklyCheckinCheckoutDTO();  // 使用默认值
        }

        // 获取每天的入住和退房人次
        List<DailyCheckinCheckoutDTO> dailySummaries = reportService.getDailyCheckinCheckoutSummary(startDate, endDate);
        if (dailySummaries == null) {
            dailySummaries = Collections.emptyList();
        }

        // 将所有结果汇总为一个 Map 以返回给前端
        result.put("weeklySummary", weeklySummary);
        result.put("dailySummaries", dailySummaries);

        // 使用 ObjectMapper 序列化为 JSON 测试
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResult = objectMapper.writeValueAsString(result);
            System.out.println("序列化后的 JSON 数据:" + jsonResult);  // 打印序列化后的 JSON 数据
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("序列化失败：" + e.getMessage());
        }
        return result;
    }
}

