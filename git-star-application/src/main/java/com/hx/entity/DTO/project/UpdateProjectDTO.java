package com.hx.entity.DTO.project;

import lombok.Data;

/**
 * @author dhx
 * @date 2025/1/9 0:58
 */
@Data
public class UpdateProjectDTO {
    private Long id;
    private String name;
    private Boolean visibility;
    private String logo;
    private String description;
}
