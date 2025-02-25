package com.hx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.common.Result;
import com.hx.entity.DTO.repository.NewRepositoryDTO;
import com.hx.entity.DTO.repository.UpdateRepositoryDTO;
import com.hx.entity.PO.Repository;

/**
 * @author dhx
 * @date 2025/1/8 16:31
 */
public interface RepositoryService {
    public Result newRepository(NewRepositoryDTO newRepositoryDTO);
    public Result delRepository(Long repositoryId);
    public Result updateRepository(UpdateRepositoryDTO updateRepositoryDTO);
    public Result getRepositoryInfo(Long repositoryId);
    public Result getRepositoryList(Page<Repository> page);
    public Result getRepositoryListByProjectId(Page<Repository> page,Long projectId);
}
