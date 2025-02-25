package com.hx.entity.DTO.user;

import lombok.Data;

/**
 * @author dhx
 * @date 2025/1/8 17:56
 */
@Data

public class RegisterDTO {
    private String username;
    private String email;
    private String verifyCode;
    private String password;
}
