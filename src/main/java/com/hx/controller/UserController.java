package com.hx.controller;

import com.hx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dhx
 * @date 2025/1/8 16:23
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;
}
