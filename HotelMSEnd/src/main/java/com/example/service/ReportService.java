package com.example.service;

import com.example.mapper.ActivitySignMapper;
import com.example.dto.WeeklyReportDTO;
import com.example.dto.DailyReportDTO;
import com.example.dto.RoomTypeReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final ActivitySignMapper activitySignMapper;

    @Autowired
    public ReportService(ActivitySignMapper activitySignMapper) {
        this.activitySignMapper = activitySignMapper;
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
}
