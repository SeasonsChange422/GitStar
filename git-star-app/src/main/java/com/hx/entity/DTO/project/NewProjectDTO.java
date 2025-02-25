package com.hx.entity.DTO.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dhx
 * @date 2025/1/9 0:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewProjectDTO {
    private String name;
    private Boolean visibility;
}
