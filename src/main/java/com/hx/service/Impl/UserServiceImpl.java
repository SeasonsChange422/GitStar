package com.hx.service.Impl;

import com.hx.mapper.UserMapper;
import com.hx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dhx
 * @date 2025/1/8 16:33
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
}
