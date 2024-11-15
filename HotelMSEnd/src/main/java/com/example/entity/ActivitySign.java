package com.example.entity;

/**
 * 功能：竞赛报名实体类
 * 日期：2024/2/27 20:49
 */
public class ActivitySign {
    private Integer id;

    private Integer roomnumber;
    private Integer activityId;
    private Integer userId;
    private String time;
    private String activityName;
    private String userName;

    private String applystart;

    private String applyend;

    private String form;

    private Integer activity_number;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
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

    public Integer getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(Integer roomnumber) {
        this.roomnumber = roomnumber;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public Integer getActivity_number() {
        return activity_number;
    }

    public void setActivity_number(Integer activity_number) {
        this.activity_number = activity_number;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

}