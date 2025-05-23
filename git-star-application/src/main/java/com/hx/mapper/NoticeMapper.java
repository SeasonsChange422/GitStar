package com.hx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hx.entity.PO.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author dhx
 * @date 2025/2/28 10:21
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    @Select("SELECT * FROM gitstar_notice")
    List<Notice> selectAll();
}
