package com.hx.entity.PO.relationship;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dhx
 * @date 2025/1/10 1:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("gitstar_project_repository")
public class ProjectRepository {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long repositoryId;
}
