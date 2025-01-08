package com.hx.service.Impl;

import com.hx.mapper.CommentMapper;
import com.hx.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dhx
 * @date 2025/1/8 16:34
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
}
