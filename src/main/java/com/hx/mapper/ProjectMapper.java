package com.hx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hx.entity.PO.Project;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author dhx
 * @date 2025/1/8 16:36
 */
@Repository
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {
}
