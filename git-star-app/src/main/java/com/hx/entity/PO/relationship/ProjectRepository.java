package com.hx.entity.PO.relationship;

import com.baomidou.mybatisplus.annotation.TableName;
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
    private Long projectId;
    private Long repositoryId;
}
