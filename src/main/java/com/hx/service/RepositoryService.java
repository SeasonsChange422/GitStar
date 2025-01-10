package com.hx.service;

import com.hx.common.Result;
import com.hx.entity.DTO.repository.DelRepositoryDTO;
import com.hx.entity.DTO.repository.NewRepositoryDTO;
import com.hx.entity.DTO.repository.UpdateRepositoryDTO;

/**
 * @author dhx
 * @date 2025/1/8 16:31
 */
public interface RepositoryService {
    public Result newRepository(NewRepositoryDTO newRepositoryDTO);
    public Result delRepository(Long repositoryId);
    public Result updateRepository(UpdateRepositoryDTO updateRepositoryDTO);
    public Result getRepositoryInfo(Long repositoryId);
}
