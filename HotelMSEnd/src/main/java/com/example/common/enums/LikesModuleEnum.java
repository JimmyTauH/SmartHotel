package com.example.common.enums;

public enum LikesModuleEnum {
    BlOG("帖子"),
    ACTIVITY("竞赛");

    private final String value;

    LikesModuleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
