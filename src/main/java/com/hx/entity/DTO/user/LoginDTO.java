package com.hx.entity.DTO.user;

import com.hx.entity.PO.User;
import lombok.Data;

/**
 * @author dhx
 * @date 2025/1/8 17:53
 */
@Data
public class LoginDTO{
    private String account;
    private String password;
}