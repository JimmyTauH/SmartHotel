package com.example.service;

import com.example.mapper.ActivitySignMapper;
import com.example.mapper.CheckIOMapper;
import com.example.dto.WeeklyReportDTO;
import com.example.dto.DailyReportDTO;
import com.example.dto.RoomTypeReportDTO;
import com.example.dto.WeeklyCheckinCheckoutDTO;
import com.example.dto.DailyCheckinCheckoutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final ActivitySignMapper activitySignMapper;
    private final CheckIOMapper checkIOMapper;

    @Autowired
    public ReportService(ActivitySignMapper activitySignMapper, CheckIOMapper checkIOMapper) {
        this.activitySignMapper = activitySignMapper;
        this.checkIOMapper = checkIOMapper;
    }

    /**
     * 获取一周内的总预定量和总预定金额
     */
    public WeeklyReportDTO getWeeklyReservationSummary(String startDate, String endDate) {
        return activitySignMapper.calculateWeeklySummary(startDate, endDate);
    }

    /**
     * 获取每天的总预定量和总预定金额
     */
    public List<DailyReportDTO> getDailyReservationSummary(String startDate, String endDate) {
        return activitySignMapper.calculateDailySummary(startDate, endDate);
    }

    /**
     * 获取按房型统计的每日和每周的总预定量和总预定金额
     */
    public List<RoomTypeReportDTO> getRoomTypeSummary(String startDate, String endDate) {
        return activitySignMapper.calculateRoomTypeSummary(startDate, endDate);
    }

    public WeeklyCheckinCheckoutDTO getWeeklyCheckinCheckoutSummary(String startDate, String endDate) {
        // 实现获取一周的总入住数据
        return checkIOMapper.calculateWeeklyCheckinCheckoutSummary(startDate, endDate);
    }

    public List<DailyCheckinCheckoutDTO> getDailyCheckinCheckoutSummary(String startDate, String endDate) {
        List<DailyCheckinCheckoutDTO> checkinData = checkIOMapper.calculateDailyCheckinSummary(startDate, endDate);
        List<DailyCheckinCheckoutDTO> checkoutData = checkIOMapper.calculateDailyCheckoutSummary(startDate, endDate);

        return DailyCheckinCheckoutDTO.mergeByDate(checkinData, checkoutData);
    }

}
