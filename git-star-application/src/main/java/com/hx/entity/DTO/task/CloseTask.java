package com.hx.entity.DTO.task;

import lombok.Data;

/**
 * @author dhx
 * @date 2025/5/15 18:08
 */
@Data
public class CloseTask {
    private Long repositoryId;
    private Long taskId;
    private String type;
}
