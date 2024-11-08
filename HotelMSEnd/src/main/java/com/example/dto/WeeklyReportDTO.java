package com.example.dto;

public class WeeklyReportDTO {
    private int totalReservations;
    private double totalAmount;

    public WeeklyReportDTO() {
        // 默认无参构造器
    }

    // Getter and Setter for totalReservations
    public int getTotalReservations() {
        return totalReservations;
    }

    public void setTotalReservations(int totalReservations) {
        this.totalReservations = totalReservations;
    }

    // Getter and Setter for totalAmount
    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}

