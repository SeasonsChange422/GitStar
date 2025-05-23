package com.hx.mapper.relationship;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hx.entity.PO.relationship.RepositoryFolder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author dhx
 * @date 2025/2/21 16:43
 */
@Repository
@Mapper
public interface RepositoryFolderMapper extends BaseMapper<RepositoryFolder> {
}
