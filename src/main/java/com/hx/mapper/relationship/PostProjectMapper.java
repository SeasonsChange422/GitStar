package com.hx.mapper.relationship;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hx.entity.PO.relationship.PostProject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author dhx
 * @date 2025/1/10 16:46
 */
@Repository
@Mapper
public interface PostProjectMapper extends BaseMapper<PostProject> {
}
