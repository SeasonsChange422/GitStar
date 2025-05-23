package com.hx.entity.DTO.user;

import com.hx.enums.UserProjectAuthEnum;
import com.hx.entity.PO.User;
import lombok.Data;

/**
 * @author dhx
 * @date 2025/5/9 15:56
 */
@Data
public class DeveloperDTO extends User {
    private UserProjectAuthEnum auth;

    public DeveloperDTO(UserProjectAuthEnum auth) {
        this.auth = auth;
    }

    public DeveloperDTO(User user, UserProjectAuthEnum auth) {
        super(user);
        this.auth = auth;
    }

}
