package com.hx.service.Impl;

import com.hx.mapper.RepositoryMapper;
import com.hx.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dhx
 * @date 2025/1/8 16:33
 */
@Service
public class RepositoryServiceImpl implements RepositoryService {
    @Autowired
    private RepositoryMapper repositoryMapper;
}
