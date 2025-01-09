package com.hx.entity.PO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author dhx
 * @date 2025/1/8 16:06
 */
@TableName("gitstar_project")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project implements Serializable {
    @TableId("project_id")
    private Long id;
    @TableField("project_name")
    private String name;
    @TableField("project_visibility")
    private Boolean visibility;
    private static final long serialVersionUID = 1L;
    public Project(String name, Boolean visibility) {
        this.name = name;
        this.visibility = visibility;
    }
}
