package com.hx.service;

import com.hx.common.Result;
import com.hx.entity.DTO.tag.DelTagDTO;
import com.hx.entity.DTO.tag.NewTagDTO;
import com.hx.entity.DTO.tag.ProjectTagDTO;
import com.hx.entity.PO.relationship.ProjectTag;

/**
 * @author dhx
 * @date 2025/5/10 10:45
 */
public interface TagService {
    public Result newTag(NewTagDTO newTagDTO);
    public Result delTag(DelTagDTO delTagDTO);
    public Result getTags();
    public Result getTagOfProject(Long projectId);
    public Result addTagToProject(ProjectTagDTO projectTagDTO);
    public Result removeTagFromProject(ProjectTagDTO projectTagDTO);
}
