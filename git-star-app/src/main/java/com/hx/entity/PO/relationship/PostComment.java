package com.hx.entity.PO.relationship;

import com.baomidou.mybatisplus.annotation.TableName;
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
    private Long postId;
    private Long commentId;
}
