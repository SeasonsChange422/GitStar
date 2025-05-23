package com.hx.entity.PO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author dhx
 * @date 2025/1/8 16:06
 */
@TableName("gitstar_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId("user_id")
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
    @TableField("user_isAdmin")
    private Boolean isAdmin;
    @TableField("user_isBan")
    private Boolean isBan;
    private static final long serialVersionUID = 1L;
    public User(String username, String email, String password, String nickname, String avatar, String description) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
        this.description = description;
    }

    public User(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.avatar = user.getAvatar();
        this.isAdmin = user.getIsAdmin();
        this.description = user.getDescription();
    }
}
