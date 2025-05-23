package com.hx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hx.entity.PO.Folder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author dhx
 * @date 2025/2/21 16:42
 */
@Repository
@Mapper
public interface FolderMapper extends BaseMapper<Folder> {
}
