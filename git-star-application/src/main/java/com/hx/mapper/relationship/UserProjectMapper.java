package com.hx.mapper.relationship;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hx.entity.PO.relationship.UserProject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author dhx
 * @date 2025/1/9 16:23
 */
@Repository
@Mapper
public interface UserProjectMapper extends BaseMapper<UserProject> {
}
