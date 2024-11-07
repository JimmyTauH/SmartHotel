package com.example.entity;

import javax.swing.text.StyledEditorKit;
import java.util.Objects;

/**
 * 功能：帖子信息
 * 日期：2024/1/25 22:04
 */
public class Blog {
    /**
     * ID
     */
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 简介
     */
    private String descr;
    /**
     * 地址
     */
    private String address;
    /**
     * 封面
     */
    private String cover;
    /**
     * 标签
     */
    private String tags;
    /**
     * 发布人ID
     */
    private Integer userId;
    /**
     * 发布日期
     */
    private String date;
    /**
     * 浏览量
     */
    private Integer readCount;
    /**
     * 分类ID
     */
    private Integer categoryId;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 发布人名称
     */
    private String userName;
    /**
     * 关联竞赛编号
     */
    private Integer contestId;

    private User user;

    /**
     * 点赞数
     */
    private Integer likesCount;
    /**
     * 用户是否点赞
     */
    private Boolean userLike;
    //收藏数
    private Integer collectCount;
    //用户是否收藏
    private Boolean userCollect;

    private Integer userBlogCount;
    private Integer userLikeCount;
    private Integer userCollectCount;

    public Integer getUserBlogCount() {
        return userBlogCount;
    }

    public void setUserBlogCount(Integer userBlogCount) {
        this.userBlogCount = userBlogCount;
    }

    public Integer getUserLikeCount() {
        return userLikeCount;
    }

    public void setUserLikeCount(Integer userLikeCount) {
        this.userLikeCount = userLikeCount;
    }

    public Integer getUserCollectCount() {
        return userCollectCount;
    }

    public void setUserCollectCount(Integer userCollectCount) {
        this.userCollectCount = userCollectCount;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Boolean getUserCollect() {
        return userCollect;
    }

    public void setUserCollect(Boolean userCollect) {
        this.userCollect = userCollect;
    }

    public Boolean getUserLike() {
        return userLike;
    }

    public void setUserLike(Boolean userLike) {
        this.userLike = userLike;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public Integer getContestId() { return  contestId; }

    public void setContestId(Integer contestId) { this.contestId = contestId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blog blog = (Blog) o;
        return Objects.equals(id, blog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}