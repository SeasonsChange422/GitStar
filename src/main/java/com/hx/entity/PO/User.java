package com.hx.entity.PO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author dhx
 * @date 2025/1/8 16:06
 */
@TableName("gitstar_user")
@Data
public class User {
    @TableId
    private Long id;
    @TableField("user_username")
    private String username;
    @TableField("user_email")
    private String email;
    @TableField("user_password")
    private String password;
    @TableField("user_nickname")
    private String nickname;
    @TableField("user_avatar")
    private String avatar;
    @TableField("user_description")
    private String description;
}
