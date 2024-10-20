package com.example.mapper;

import com.example.entity.Activity;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


/**
 * 功能：竞赛表
 * 日期：2024/1/31 20:55
 */
public interface ActivityMapper {

    void add(Activity activity);

    void deleteById(Integer id);

    void updateById(Activity activity);

    Activity selectById(Integer id);

    List<Activity> selectAll(Activity activity);

    @Select("SELECT * FROM Activity WHERE name LIKE #{name}")
    List<Activity> selectByName(String name);

    List<Activity> selectBlogTop();

    @Update("update activity set read_count = read_count + 1 where id = #{activityId}")
    void updateCount(Integer activityId);

    List<Activity> selectUser(Activity activity);

    List<Activity> selectLike(Activity activity);

    List<Activity> selectCollect(Activity activity);

    List<Activity> selectComment(Activity activity);
}