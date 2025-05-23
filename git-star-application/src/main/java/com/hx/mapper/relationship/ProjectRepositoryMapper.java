package com.hx.mapper.relationship;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hx.entity.PO.relationship.ProjectRepository;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author dhx
 * @date 2025/1/10 1:28
 */
@Repository
@Mapper
public interface ProjectRepositoryMapper extends BaseMapper<ProjectRepository> {
}
