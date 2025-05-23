package com.hx.entity.PO.relationship;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author dhx
 * @date 2025/2/25 13:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("gitstar_post_comment")
public class PostComment{
    @JsonSerialize(using = ToStringSerializer.class)
    private Long postId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentId;
}
