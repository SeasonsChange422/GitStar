package com.hx.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author dhx
 * @date 2025/1/9 16:14
 */

public enum UserProjectAuthEnum {
    DEVELOPER("developer"),
    ADMINISTRATOR("administrator");
    @EnumValue
    private String desc;
    UserProjectAuthEnum(String desc){
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }
}
