package com.example.entity;

import java.time.LocalDateTime;

public class ChatHistory {
    private String from;  // 从哪个用户发送
    private String to;    // 发送给哪个用户
    private String message; // 消息内容
    private LocalDateTime timestamp; // 消息时间

    public ChatHistory(String from, String to, String message, LocalDateTime timestamp) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getter 和 Setter 方法
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return timestamp + " " + from + " -> " + to + ": " + message;
    }
}
