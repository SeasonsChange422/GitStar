package com.hx.controller;

import com.hx.aop.annotation.UserAuth;
import com.hx.common.Result;
import com.hx.entity.DTO.user.LoginDTO;
import com.hx.entity.DTO.user.RegisterDTO;
import com.hx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dhx
 * @date 2025/1/8 16:23
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/user/login")
    public Result login(@RequestBody LoginDTO loginDTO){
        return userService.login(loginDTO);
    }
    @GetMapping("/api/user/sendVerifyCode")
    public Result sendVerifyCode(@RequestParam String email){
        return userService.sendVerifyCode(email);
    }
    @PostMapping("/api/user/register")
    public Result register(@RequestBody RegisterDTO registerDTO){
        return userService.register(registerDTO);
    }
    @UserAuth()
    @GetMapping("/api/user/getUserInfo")
    public Result getUserInfo(@RequestHeader("token") String token){
        return userService.getUserInfo(token);
    }
}
