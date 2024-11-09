package com.example.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class DailyCheckinCheckoutDTO {
    private String date;
    private int checkins;
    private int checkouts;

    public DailyCheckinCheckoutDTO() {
        // 默认无参构造器
    }

    // 构造方法
    public DailyCheckinCheckoutDTO(String date, int checkins, int checkouts) {
        this.date = date;
        this.checkins = checkins;
        this.checkouts = checkouts;
    }

    // Getters and Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCheckins() {
        return checkins;
    }

    public void setCheckins(int checkins) {
        this.checkins = checkins;
    }

    public int getCheckouts() {
        return checkouts;
    }

    public void setCheckouts(int checkouts) {
        this.checkouts = checkouts;
    }

    // 静态方法，用于根据日期合并两个列表
    public static List<DailyCheckinCheckoutDTO> mergeByDate(List<DailyCheckinCheckoutDTO> checkinData, List<DailyCheckinCheckoutDTO> checkoutData) {
        Map<String, DailyCheckinCheckoutDTO> resultMap = new HashMap<>();

        // 将 checkin 数据加入 resultMap
        for (DailyCheckinCheckoutDTO checkin : checkinData) {
            resultMap.putIfAbsent(checkin.getDate(), new DailyCheckinCheckoutDTO(checkin.getDate(), 0, 0));
            resultMap.get(checkin.getDate()).setCheckins(checkin.getCheckins());
        }

        // 将 checkout 数据加入 resultMap
        for (DailyCheckinCheckoutDTO checkout : checkoutData) {
            resultMap.putIfAbsent(checkout.getDate(), new DailyCheckinCheckoutDTO(checkout.getDate(), 0, 0));
            resultMap.get(checkout.getDate()).setCheckouts(checkout.getCheckouts());
        }

        // 转换为列表返回
        return new ArrayList<>(resultMap.values());
    }
}


