package com.hx.service.Impl;

import com.hx.common.Result;
import com.hx.entity.DTO.repository.DelRepositoryDTO;
import com.hx.entity.DTO.repository.NewRepositoryDTO;
import com.hx.entity.DTO.repository.UpdateRepositoryDTO;
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

    @Override
    public Result newRepository(NewRepositoryDTO newRepositoryDTO, String token) {
        return null;
    }

    @Override
    public Result delRepository(Long repositoryId) {
        return null;
    }

    @Override
    public Result updateRepository(UpdateRepositoryDTO updateRepositoryDTO) {
        return null;
    }

    @Override
    public Result getRepositoryInfo(Long repositoryId) {
        return null;
    }
}
