package com.example.mapper;

import com.example.entity.Activity;
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

    void updateById(ActivitySign activitySign);

    void add(ActivitySign activitySign);



    @Insert("insert into activity_sign (roomnumber, activity_id, user_id, time,applystart,applyend,activity_number,form) VALUES (#{roomnumber},#{activityId},#{userId},#{time},#{applystart},#{applyend},#{activity_number},#{form})")
    void insert(ActivitySign activitySign);

    @Select("select * from activity_sign where activity_id = #{activityId} and user_id = #{userId}")
    ActivitySign selectByActivityIdAndUserId(@Param("activityId") Integer actId,@Param("userId") Integer userId);

    List<ActivitySign> selectAll(ActivitySign activitySign);
	@Select("select activity_id from activity_sign where user_id = #{userId}")
    Integer selectActIdByUserId(@Param("userId") Integer userId);

    @Delete("delete from activity_sign where id = #{id}")
    void deleteById(Integer id);

    @Delete("delete from activity_sign where activity_id = #{activityId} and user_id = #{userId}")
    void userDelete(@Param("activityId") Integer activityId,  @Param("userId") Integer useId);

    // 1. 一周总预定量和总预定金额
    // 1. 一周的总预定量和总预定金额
    @Select("SELECT COUNT(*) AS totalReservations, " +
            "SUM(CAST(REGEXP_SUBSTR(a.host, '^[0-9]+') AS UNSIGNED)) AS totalAmount " +
            "FROM activity_sign s " +
            "JOIN activity a ON s.activity_id = a.id " +
            "WHERE s.time BETWEEN #{startDate} AND #{endDate}")
    WeeklyReportDTO calculateWeeklySummary(@Param("startDate") String startDate, @Param("endDate") String endDate);

    // 2. 每天的总预定量和总预定金额
    @Select("SELECT s.time AS date, COUNT(*) AS totalReservations, " +
            "SUM(CAST(REGEXP_SUBSTR(a.host, '^[0-9]+') AS UNSIGNED)) AS totalAmount " +
            "FROM activity_sign s " +
            "JOIN activity a ON s.activity_id = a.id " +
            "WHERE s.time BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY s.time ORDER BY s.time")
    List<DailyReportDTO> calculateDailySummary(@Param("startDate") String startDate, @Param("endDate") String endDate);

    // 3. 按房型统计的每日和每周的总预定量和总预定金额
    @Select("SELECT s.activity_id AS roomType, s.time AS date, COUNT(*) AS totalReservations, " +
            "SUM(CAST(REGEXP_SUBSTR(a.host, '^[0-9]+') AS UNSIGNED)) AS totalAmount " +
            "FROM activity_sign s " +
            "JOIN activity a ON s.activity_id = a.id " +
            "WHERE s.time BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY s.activity_id, s.time ORDER BY s.activity_id, s.time")
    List<RoomTypeReportDTO> calculateRoomTypeSummary(@Param("startDate") String startDate, @Param("endDate") String endDate);

}
