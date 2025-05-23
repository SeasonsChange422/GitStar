package com.hx.entity.PO.relationship;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dhx
 * @date 2025/1/10 16:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("gitstar_post_project")
public class PostProject {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long postId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;
}
