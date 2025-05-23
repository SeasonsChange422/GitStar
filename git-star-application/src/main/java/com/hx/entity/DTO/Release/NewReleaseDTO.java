package com.hx.entity.DTO.Release;

import lombok.Data;

/**
 * @author dhx
 * @date 2025/5/9 19:59
 */
@Data
public class NewReleaseDTO {
    private Long projectId;
    private String file;
    private String description;
}
