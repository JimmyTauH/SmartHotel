package com.example.dto;

public class MonthlyCheckinCheckoutDTO {

    private int totalCheckins;
    private int totalCheckouts;

    public MonthlyCheckinCheckoutDTO() {
        // 默认无参构造器
    }

    // 构造方法
    public MonthlyCheckinCheckoutDTO(String week, int totalCheckins, int totalCheckouts) {
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