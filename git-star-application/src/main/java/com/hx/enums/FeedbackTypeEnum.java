package com.hx.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author dhx
 * @date 2025/5/14 15:53
 */
public enum FeedbackTypeEnum {
    PROJECT("project"),
    REPOSITORY("repository"),
    USER("user"),
    POST("post"),
    COMMENT("comment"),
    COMMON("common"),
    RELEASE("release");
    @EnumValue
    private String type;
    FeedbackTypeEnum(String type) {this.type = type;}
    public String getType() {
        return type;
    }

}
