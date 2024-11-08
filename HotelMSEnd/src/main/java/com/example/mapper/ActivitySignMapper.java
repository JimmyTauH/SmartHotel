package com.example.mapper;

import com.example.entity.ActivitySign;
import com.example.dto.DailyReportDTO;
import com.example.dto.RoomTypeReportDTO;
import com.example.dto.WeeklyReportDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ActivitySignMapper {
    @Insert("insert into activity_sign (activity_id, user_id, time,start_date,end_date,activity_number) VALUES (#{activityId},#{userId},#{time},#{start_date},#{end_date},#{activity_number})")
    void insert(ActivitySign activitySign);

    @Select("select * from activity_sign where activity_id = #{activityId} and user_id = #{userId}")
    ActivitySign selectByActivityIdAndUserId(@Param("activityId") Integer actId,@Param("userId") Integer userId);
    
	@Select("select activity_id from activity_sign where user_id = #{userId}")
    Integer selectActIdByUserId(@Param("userId") Integer userId);

    List<ActivitySign> selectAll(ActivitySign activitySign);

    @Delete("delete from activity_sign where id = #{id}")
    void deleteById(Integer id);

    @Delete("delete from activity_sign where activity_id = #{activityId} and user_id = #{userId}")
    void userDelete(@Param("activityId") Integer activityId,  @Param("userId") Integer useId);

    // 1. 一周总预定量和总预定金额
    @Select("SELECT COUNT(*) AS totalReservations, " +
            "SUM(prize) AS totalAmount " +
            "FROM activity_sign s " +
            "JOIN activity a ON s.activity_id = a.id " +
            "WHERE s.time BETWEEN #{startDate} AND #{endDate}")
    WeeklyReportDTO calculateWeeklySummary(@Param("startDate") String startDate, @Param("endDate") String endDate);

    // 2. 每天的总预定量和总预定金额
    @Select("SELECT s.time AS date, COUNT(*) AS totalReservations, " +
            "SUM(prize) AS totalAmount " +
            "FROM activity_sign s " +
            "JOIN activity a ON s.activity_id = a.id " +
            "WHERE s.time BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY s.time ORDER BY s.time")
    List<DailyReportDTO> calculateDailySummary(@Param("startDate") String startDate, @Param("endDate") String endDate);

    // 3. 按房型统计的每日和每周的总预定量和总预定金额
    @Select("SELECT s.activity_id AS roomType, s.time AS date, COUNT(*) AS totalReservations, " +
            "SUM(prize) AS totalAmount " +
            "FROM activity_sign s " +
            "JOIN activity a ON s.activity_id = a.id " +
            "WHERE s.time BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY s.activity_id, s.time ORDER BY s.activity_id, s.time")
    List<RoomTypeReportDTO> calculateRoomTypeSummary(@Param("startDate") String startDate, @Param("endDate") String endDate);

}
