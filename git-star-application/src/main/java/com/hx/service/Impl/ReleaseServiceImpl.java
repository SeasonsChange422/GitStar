package com.hx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.entity.DTO.Release.DelReleaseDTO;
import com.hx.entity.DTO.Release.NewReleaseDTO;
import com.hx.entity.DTO.Release.UpdateReleaseDTO;
import com.hx.entity.PO.Release;
import com.hx.entity.PO.relationship.ProjectRelease;
import com.hx.mapper.ReleaseMapper;
import com.hx.mapper.relationship.ProjectReleaseMapper;
import com.hx.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dhx
 * @date 2025/5/9 19:58
 */
@Service
public class ReleaseServiceImpl implements ReleaseService {
    @Autowired
    private ReleaseMapper releaseMapper;
    @Autowired
    private ProjectReleaseMapper projectReleaseMapper;
    @Override
    public Result newRelease(NewReleaseDTO newReleaseDTO) {
        try {
            Release release = new Release(newReleaseDTO.getFile(),newReleaseDTO.getDescription());
            releaseMapper.insert(release);
            projectReleaseMapper.insert(new ProjectRelease(newReleaseDTO.getProjectId(),release.getId()));
            return Result.okResult();
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result delRelease(DelReleaseDTO delReleaseDTO) {
        try {
            projectReleaseMapper.delete(new QueryWrapper<ProjectRelease>().eq("release_id",delReleaseDTO.getReleaseId()));
            releaseMapper.deleteById(delReleaseDTO.getReleaseId());
            return Result.okResult();
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result updateRelease(UpdateReleaseDTO updateReleaseDTO) {
        try {
            Release release = releaseMapper.selectById(updateReleaseDTO.getReleaseId());
            release.setDescription(updateReleaseDTO.getDescription());
            release.setFile(updateReleaseDTO.getFile());
            releaseMapper.updateById(release);
            return Result.okResult();
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getReleaseOfProject(Long projectId) {
        try{
            List<ProjectRelease>projectReleases = projectReleaseMapper.selectList(new QueryWrapper<ProjectRelease>().eq("project_id",projectId));
            List<Long> releaseIds = projectReleases.stream().map(ProjectRelease::getReleaseId).collect(Collectors.toList());
            if(releaseIds.isEmpty())return Result.okResult(new ArrayList<>());
            List<Release> releases = releaseMapper.selectBatchIds(releaseIds);
            return Result.okResult(releases);
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getReleaseInfo(Long releaseId) {
        try{
            Release release = releaseMapper.selectById(releaseId);
            return Result.okResult(release);
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }
}
