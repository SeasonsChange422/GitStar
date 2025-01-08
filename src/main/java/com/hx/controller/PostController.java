package com.hx.controller;

import com.hx.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dhx
 * @date 2025/1/8 16:25
 */
@RestController
public class PostController {
    @Autowired
    private PostService postService;
}
