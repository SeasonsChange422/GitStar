package com.hx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hx.entity.PO.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author dhx
 * @date 2025/5/10 10:37
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    @Select("SELECT gtag_id AS id, gtag_name AS name FROM gitstar_tag")
    List<Tag> selectAll();
}
