package com.hx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hx.entity.PO.Project;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dhx
 * @date 2025/1/8 16:36
 */
@Repository
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {
    @Select("SELECT * FROM gitstar_project WHERE project_name LIKE CONCAT('%', #{keyword}, '%') OR project_description LIKE CONCAT('%', #{keyword}, '%')")
    List<Project> searchProjects(@Param("keyword")String key);
}
