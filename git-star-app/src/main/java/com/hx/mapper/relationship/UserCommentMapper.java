package com.hx.mapper.relationship;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hx.entity.PO.relationship.UserComment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author dhx
 * @date 2025/2/25 3:06
 */
@Repository
@Mapper
public interface UserCommentMapper extends BaseMapper<UserComment> {
}
