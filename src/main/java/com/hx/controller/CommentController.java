package com.hx.controller;

import com.hx.service.CommentService;
import com.hx.service.Impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dhx
 * @date 2025/1/8 16:24
 */
@RestController
public class CommentController {
    @Autowired
    private CommentService service;
}