package com.hx.entity.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dhx
 * @date 2025/1/9 0:25
 */
@Data
public class UpdateUserDTO {
    private String nickname;
    private String description;
    private String avatar;
}
