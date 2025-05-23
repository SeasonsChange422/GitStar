package com.hx.service;

import com.hx.common.Result;
import com.hx.entity.DTO.user.LoginDTO;
import com.hx.entity.DTO.user.RegisterDTO;
import com.hx.entity.DTO.user.UpdateUserDTO;
import com.hx.entity.PO.User;

/**
 * @author dhx
 * @date 2025/1/8 16:31
 */
public interface UserService {
 public Result register(RegisterDTO registerDTO);
 public Result sendVerifyCode(String email);
 public Result login(LoginDTO loginDTO);
 public Result getUserInfo(String token);
 public Result updateUserInfo(UpdateUserDTO updateUserDTO, String token);
 public Result getUserById(Long userId);
}
