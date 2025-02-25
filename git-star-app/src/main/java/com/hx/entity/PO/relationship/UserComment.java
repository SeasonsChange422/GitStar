package com.hx.entity.PO.relationship;

import com.baomidou.mybatisplus.annotation.TableName;
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
    private Long userId;
    private Long commentId;
}
