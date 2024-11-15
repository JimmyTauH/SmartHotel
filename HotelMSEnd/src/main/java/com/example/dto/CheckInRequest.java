package com.example.dto;

import java.util.List;

public class CheckInRequest {
    private Integer roomId; // 房间ID
    private List<Guest> guests; // 客人信息列表

    // 默认无参构造方法
    public CheckInRequest() {
    }

    // 带参构造方法
    public CheckInRequest(Integer roomId, List<Guest> guests) {
        this.roomId = roomId;
        this.guests = guests;
    }

    // Getters and Setters
    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    /**
     * 内部类表示客人信息
     */
    public static class Guest {
        private String name; // 姓名
        private String gender; // 性别
        private String idCard; // 身份证号
        private String phone; // 联系电话

        // 默认无参构造方法
        public Guest() {
        }

        // 带参构造方法
        public Guest(String name, String gender, String idCard, String phone) {
            this.name = name;
            this.gender = gender;
            this.idCard = idCard;
            this.phone = phone;
        }

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}