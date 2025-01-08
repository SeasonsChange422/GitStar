package com.hx.service.Impl;

import com.hx.mapper.ProjectMapper;
import com.hx.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dhx
 * @date 2025/1/8 16:33
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectMapper projectMapper;
}
