package com.example.entity;
/**
 * 功能：竞赛管理实体类
 * 日期：2024/1/31 20:48
 */
public class Activity {

    /** ID */
    private Integer id;
    /** 竞赛名称 */
    private String name;
    /** 竞赛简介 */
    private String descr;
    /** 开始时间（报名） */
    private String applystart;
    /** 结束时间 */
    private String applyend;
    /** 开始时间 */
    private String form;

    /** 主办方 */
    private String host;

    /** 房型数量 */
    private Integer number;
    /** 浏览量 */
    private Integer readCount;
    /** 点赞量 */
    private Integer likeCount;
    /** 收藏量 */
    private Integer collectCount;
    private String content;
    private String cover;

    //报名是否开始
    private Boolean isApplyStart;
    //报名是否结束
    private Boolean isApplyEnd;

    //是否报名
    private Boolean isSign;

    //是否点过赞
    private Boolean isLike;
    //是否收过藏
    private Boolean isCollect;
    //用户ID
    private Integer userId;

    private Integer hotelID;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getIsLike() {
        return isLike;
    }

    public void setIsLike(Boolean like) {
        isLike = like;
    }

    public Boolean getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Boolean collect) {
        isCollect = collect;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Boolean getIsSign() {
        return isSign;
    }

    public void setIsSign(Boolean sign) {
        isSign = sign;
    }

    public Boolean getIsApplyStart() { return isApplyStart;}

    public void setIsApplyStart(Boolean applyStart) {
        isApplyStart = applyStart;
    }

    public Boolean getIsApplyEnd() { return isApplyEnd;}

    public void setIsApplyEnd(Boolean applyEnd) {
        isApplyEnd = applyEnd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getApplystart() {
        return applystart;
    }

    public void setApplystart(String applystart) {
        this.applystart = applystart;
    }

    public String getApplyend() {
        return applyend;
    }

    public void setApplyend(String  applyend) {
        this.applyend = applyend;
    }


    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }


    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getHotelID() { return  hotelID; }

    public void setHotelID(Integer hotelID) { this.hotelID = hotelID; }
}