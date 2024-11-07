package com.example.entity;

import java.io.Serializable;

/**
 * 公告信息表
*/
public class ServiceBook implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    /** 标题 */
    private String title;
    /** 内容 */
    private String content;
    /** 创建时间 */
    private String time;
    /** 创建人 */
    private Integer user;

    private Integer hotel;

    private String room;

    private boolean state;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getHotel() {
        return hotel;
    }

    public void setHotel(Integer hotel) {
        this.hotel = hotel;
    }

    public String getRoom() {return room; }

    public void setRoom(String room){ this.room = room;}

    public boolean getState(){ return state; }

    public void setState(boolean state) { this.state = state; }

}