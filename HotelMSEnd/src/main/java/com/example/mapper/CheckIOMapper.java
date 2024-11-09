package com.example.mapper;

import com.example.dto.DailyCheckinCheckoutDTO;
import com.example.dto.WeeklyCheckinCheckoutDTO;
import com.example.dto.RoomTypeReportDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CheckIOMapper {

    // 1. 一周的总入住量
    @Select("SELECT COUNT(*) AS totalCheckins " +
            "FROM check_in " +
            "WHERE intime BETWEEN #{startDate} AND #{endDate}")
    WeeklyCheckinCheckoutDTO calculateWeeklyCheckinCheckoutSummary(@Param("startDate") String startDate,
                                                                   @Param("endDate") String endDate);

    // 2. 每天的入住人次
    @Select("SELECT DATE(intime) AS date, COUNT(*) AS checkins " +
            "FROM check_in " +
            "WHERE intime BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY DATE(intime) " +
            "ORDER BY DATE(intime)")
    List<DailyCheckinCheckoutDTO> calculateDailyCheckinSummary(@Param("startDate") String startDate,
                                                               @Param("endDate") String endDate);

    // 3. 每天的退房人次
    @Select("SELECT DATE(outtime) AS date, COUNT(*) AS checkouts " +
            "FROM check_out " +
            "WHERE outtime BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY DATE(outtime) " +
            "ORDER BY DATE(outtime)")
    List<DailyCheckinCheckoutDTO> calculateDailyCheckoutSummary(@Param("startDate") String startDate,
                                                                @Param("endDate") String endDate);

    // 4. 每个房型的总预定量和总预定金额（可选）
    @Select("SELECT r.activity_id AS roomType, COUNT(*) AS totalReservations, " +
            "SUM(CAST(REGEXP_SUBSTR(a.host, '^[0-9]+') AS UNSIGNED)) AS totalAmount " +
            "FROM check_in c " +
            "JOIN room r ON c.room_id = r.id " +
            "JOIN activity a ON r.activity_id = a.id " +
            "WHERE c.intime BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY r.activity_id")
    List<RoomTypeReportDTO> calculateRoomTypeSummary(@Param("startDate") String startDate,
                                                     @Param("endDate") String endDate);
}