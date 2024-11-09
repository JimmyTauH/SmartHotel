package com.example.dto;

public class WeeklyCheckinCheckoutDTO{

    private int totalCheckins;
    private int totalCheckouts;

    public WeeklyCheckinCheckoutDTO() {
        // 默认无参构造器
    }

    // 构造方法
    public WeeklyCheckinCheckoutDTO(String week, int totalCheckins, int totalCheckouts) {
        this.totalCheckins = totalCheckins;
        this.totalCheckouts = totalCheckouts;
    }

    // Getter 和 Setter
    public int getTotalCheckins() {
        return totalCheckins;
    }

    public void setTotalCheckins(int totalCheckins) {
        this.totalCheckins = totalCheckins;
    }

    public int getTotalCheckouts() {
        return totalCheckouts;
    }

    public void setTotalCheckouts(int totalCheckouts) {
        this.totalCheckouts = totalCheckouts;
    }
}