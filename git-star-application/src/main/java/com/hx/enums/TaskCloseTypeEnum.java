package com.hx.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;


/**
 * @author dhx
 * @date 2025/5/15 17:44
 */
public enum TaskCloseTypeEnum{
    COMPLETED("completed"),
    TERMINATE("terminate");
    @EnumValue
    private String type;
    TaskCloseTypeEnum(String type) {this.type = type;}
    public String getType() {
        return type;
    }

}
