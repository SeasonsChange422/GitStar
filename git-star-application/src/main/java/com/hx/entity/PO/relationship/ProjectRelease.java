package com.hx.entity.PO.relationship;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dhx
 * @date 2025/5/9 19:53
 */
@TableName("gitstar_project_release")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRelease {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long releaseId;
}
