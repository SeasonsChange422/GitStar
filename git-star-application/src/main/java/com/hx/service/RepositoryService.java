package com.hx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.common.Result;
import com.hx.entity.DTO.repository.NewRepositoryDTO;
import com.hx.entity.DTO.repository.UpdateRepositoryDTO;
import com.hx.entity.PO.Repository;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dhx
 * @date 2025/1/8 16:31
 */
public interface RepositoryService {
    public Result newRepository(NewRepositoryDTO newRepositoryDTO);
    public Result delRepository(Long repositoryId);
    public Result updateRepository(UpdateRepositoryDTO updateRepositoryDTO);
    public Result getRepositoryInfo(Long repositoryId,String token);
    public Result getRepositoryList(Page<Repository> page,String token);
    public Result getRepositoryListByProjectId(Page<Repository> page,Long projectId,String token);
    public Result uploadRepositoryFile(Long repositoryId, String path,MultipartFile file);
    public Result getRepositoryFolder(Long repositoryId,String path);
    public Result getRepositoryFile(Long repositoryId,String path);
    public Result getRepositoryREADME(Long repositoryId);
    public Result starRepository(Long repositoryId,String token);
    public Result downloadRepository(Long repositoryId);
}
