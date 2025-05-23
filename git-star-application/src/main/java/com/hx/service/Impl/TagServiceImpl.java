package com.hx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.entity.DTO.tag.DelTagDTO;
import com.hx.entity.DTO.tag.NewTagDTO;
import com.hx.entity.DTO.tag.ProjectTagDTO;
import com.hx.entity.PO.Tag;
import com.hx.entity.PO.relationship.ProjectTag;
import com.hx.mapper.TagMapper;
import com.hx.mapper.relationship.ProjectTagMapper;
import com.hx.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dhx
 * @date 2025/5/10 10:46
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ProjectTagMapper projectTagMapper;
    @Override
    public Result newTag(NewTagDTO newTagDTO) {
        try {
            Tag tag = tagMapper.selectOne(new QueryWrapper<Tag>().eq("gtag_name",newTagDTO.getName()));
            if(tag!=null)return Result.okResult(tag);
            Tag insertTag = new Tag(newTagDTO.getName());
            tagMapper.insert(insertTag);
            return Result.okResult();
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result delTag(DelTagDTO delTagDTO) {
        try{
            System.out.println(delTagDTO.getId());
            tagMapper.deleteById(delTagDTO.getId());
            return Result.okResult();
        }catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getTags() {
        try{
            return Result.okResult(tagMapper.selectAll());
        }catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getTagOfProject(Long projectId) {
        try {
            List<ProjectTag> projectTags = projectTagMapper.selectList(new QueryWrapper<ProjectTag>().eq("project_id",projectId));
            List<Long> tagIds = projectTags.stream().map(ProjectTag::getTagId).collect(Collectors.toList());
            if(tagIds.isEmpty())return Result.okResult(new ArrayList<>());
            List<Tag> tags = tagMapper.selectBatchIds(tagIds);
            return Result.okResult(tags);
        }catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result addTagToProject(ProjectTagDTO projectTagDTO) {
        try{
            ProjectTag projectTag = new ProjectTag(projectTagDTO.getProjectId(), projectTagDTO.getTagId());
            projectTagMapper.insert(projectTag);
            return Result.okResult();
        }catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result removeTagFromProject(ProjectTagDTO projectTagDTO) {
        try{
            projectTagMapper.delete(new QueryWrapper<ProjectTag>().eq("project_id",projectTagDTO.getProjectId()).eq("tag_id",projectTagDTO.getTagId()));
            return Result.okResult();
        }catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }


}
