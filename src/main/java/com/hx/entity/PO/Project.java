package com.hx.entity.PO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author dhx
 * @date 2025/1/8 16:06
 */
@TableName("gitstar_project")
@Data
public class Project {
    @TableId
    private Long id;
    @TableField("project_name")
    private String name;
    @TableField("project_visibility")
    private Boolean visibility;
}
