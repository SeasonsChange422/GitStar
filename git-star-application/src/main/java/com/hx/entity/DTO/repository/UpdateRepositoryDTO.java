package com.hx.entity.DTO.repository;

import lombok.Data;

/**
 * @author dhx
 * @date 2025/1/9 22:10
 */
@Data
public class UpdateRepositoryDTO {
    private Long repositoryId;
    private String name;
    private String description;
}
