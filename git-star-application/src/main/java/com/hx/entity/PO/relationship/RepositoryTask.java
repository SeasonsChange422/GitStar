package com.hx.entity.PO.relationship;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dhx
 * @date 2025/5/15 17:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("gitstar_repository_task")
public class RepositoryTask {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("repository_id")
    private Long repositoryId;
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("task_id")
    private Long taskId;
}
