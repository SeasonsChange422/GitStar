package com.hx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.entity.DTO.repository.NewRepositoryDTO;
import com.hx.entity.DTO.repository.UpdateRepositoryDTO;
import com.hx.entity.PO.Folder;
import com.hx.entity.PO.Project;
import com.hx.entity.PO.Repository;
import com.hx.entity.PO.relationship.ProjectRepository;
import com.hx.entity.PO.relationship.RepositoryFolder;
import com.hx.entity.PO.relationship.UserProject;
import com.hx.mapper.FolderMapper;
import com.hx.mapper.RepositoryMapper;
import com.hx.mapper.relationship.ProjectRepositoryMapper;
import com.hx.mapper.relationship.RepositoryFolderMapper;
import com.hx.service.RepositoryService;
import com.hx.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private FolderMapper folderMapper;
    @Autowired
    private RepositoryFolderMapper repositoryFolderMapper;
    @Override
    public Result newRepository(NewRepositoryDTO newRepositoryDTO) {
        Repository repository = new Repository(newRepositoryDTO.getName());
        Folder folder = new Folder(repository.getName());
        if(repositoryMapper.insert(repository)==1&&
                projectRepositoryMapper.insert(new ProjectRepository(newRepositoryDTO.getProjectId(),repository.getId()))==1&&
                folderMapper.insert(folder)==1&&
                repositoryFolderMapper.insert(new RepositoryFolder(repository.getId(),folder.getFolderId()))==1){
            return Result.okResult(200,"添加成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result delRepository(Long repositoryId) {
        if(projectRepositoryMapper.delete(new QueryWrapper<ProjectRepository>().eq("repository_id",repositoryId))==1
                &&repositoryMapper.deleteById(repositoryId)==1){
            //TODO 删除文件夹
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

    @Override
    public Result getRepositoryList(Page<Repository> page) {
        Page<Repository> repositoryPage = repositoryMapper.selectPage(page,new QueryWrapper<Repository>().eq("repository_visibility",true));
        return Result.okResult(repositoryPage);
    }

    @Override
    public Result getRepositoryListByProjectId(Page<Repository> page, Long projectId) {
        List<ProjectRepository> list = projectRepositoryMapper.selectList(new QueryWrapper<ProjectRepository>().eq("project_id",projectId));
        List<Long> repositoryIds = list.stream()
                .map(ProjectRepository::getRepositoryId)
                .collect(Collectors.toList());
        if (repositoryIds.isEmpty()) {
            return Result.okResult(new ArrayList<>());
        }
        Page<Repository> repositoryPage = repositoryMapper.selectPage(page,new QueryWrapper<Repository>()
                .in("repository_id", repositoryIds));
        return Result.okResult(repositoryPage);
    }
}
