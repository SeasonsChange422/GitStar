package com.hx.entity.PO.relationship;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dhx
 * @date 2025/1/10 17:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("gitstar_user_post")
public class UserPost {
    private Long userId;
    private Long postId;
}
