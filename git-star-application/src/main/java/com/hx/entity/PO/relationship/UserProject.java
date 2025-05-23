package com.hx.entity.PO.relationship;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hx.enums.UserProjectAuthEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dhx
 * @date 2025/1/9 16:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("gitstar_user_project")
public class UserProject {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;
    private UserProjectAuthEnum level;
}
