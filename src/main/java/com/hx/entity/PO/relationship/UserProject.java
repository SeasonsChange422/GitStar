package com.hx.entity.PO.relationship;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hx.common.enums.UserProjectAuthEnum;
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
    private Long userId;
    private Long projectId;
    private UserProjectAuthEnum level;
}
