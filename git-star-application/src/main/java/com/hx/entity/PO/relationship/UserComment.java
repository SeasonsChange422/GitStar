package com.hx.entity.PO.relationship;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dhx
 * @date 2025/2/25 2:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("gitstar_user_comment")
public class UserComment {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentId;
}
