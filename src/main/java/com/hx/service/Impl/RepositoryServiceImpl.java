package com.hx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.entity.DTO.repository.DelRepositoryDTO;
import com.hx.entity.DTO.repository.NewRepositoryDTO;
import com.hx.entity.DTO.repository.UpdateRepositoryDTO;
import com.hx.entity.PO.Repository;
import com.hx.entity.PO.relationship.ProjectRepository;
import com.hx.mapper.RepositoryMapper;
import com.hx.mapper.relationship.ProjectRepositoryMapper;
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
    @Autowired
    private ProjectRepositoryMapper projectRepositoryMapper;
    @Override
    public Result newRepository(NewRepositoryDTO newRepositoryDTO) {
        Repository repository = new Repository(newRepositoryDTO.getName());
        if(repositoryMapper.insert(repository)==1&&projectRepositoryMapper.insert(new ProjectRepository(newRepositoryDTO.getProjectId(),repository.getId()))==1){
            return Result.okResult(200,"添加成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result delRepository(Long repositoryId) {
        if(projectRepositoryMapper.delete(new QueryWrapper<ProjectRepository>().eq("repository_id",repositoryId))==1
                &&repositoryMapper.deleteById(repositoryId)==1){
            return Result.okResult(200,"删除成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result updateRepository(UpdateRepositoryDTO updateRepositoryDTO) {
        if(repositoryMapper.updateById(new Repository(updateRepositoryDTO.getRepositoryId(), updateRepositoryDTO.getName()))==1){
            return Result.okResult(200,"修改成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result getRepositoryInfo(Long repositoryId) {
        Repository repository = repositoryMapper.selectById(repositoryId);
        if(repository!=null){
            return Result.okResult(repositoryMapper.selectById(repositoryId));
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
}
