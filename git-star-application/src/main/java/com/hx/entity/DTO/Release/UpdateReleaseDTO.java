package com.hx.entity.DTO.Release;

import lombok.Data;

/**
 * @author dhx
 * @date 2025/5/9 20:08
 */
@Data
public class UpdateReleaseDTO {
    private Long releaseId;
    private String file;
    private String description;
}
