package com.hx.entity.PO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author dhx
 * @date 2025/1/8 16:08
 */
@TableName("gitstar_repository")
@Data
public class Repository {
    @TableId
    private Long id;
    @TableField("repository_name")
    private String name;
    @TableField("repository_visibility")
    private String visibility;
}
