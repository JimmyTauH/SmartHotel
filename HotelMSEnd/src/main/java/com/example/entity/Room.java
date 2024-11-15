package com.example.entity;

public class Room {
    private Integer id;
    private String name;  // 房间号
    private String status; // 房间状态，如 "AVAILABLE" 或 "OCCUPIED"

    // Getters and Setters
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}