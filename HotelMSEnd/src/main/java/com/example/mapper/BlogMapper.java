package com.example.mapper;

import com.example.entity.Activity;
import com.example.entity.Blog;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BlogMapper {
    void insert(Blog blog);

    void updateById(Blog blog);

    void deleteById(Integer id);

    Blog selectById(Integer id);

    List<Blog> selectAll(Blog blog);

    List<Blog> selectBlogTop();

    @Select("select * from blog where user_id = #{userId}")
    List<Blog> selectUserBlogCount(Integer userId);

    @Select("SELECT * FROM blog WHERE title LIKE #{title}")
    List<Blog> selectBytitle(String title);
    @Update("update blog set read_count = read_count+1 where id = #{blogId}")
    void updateCount(Integer blogId);

    List<Blog> selectLike(Blog blog);

    List<Blog> selectCollect(Blog blog);

    List<Blog> selectComment(Blog blog);
}
