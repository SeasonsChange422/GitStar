package com.hx.entity.DTO.post;

import lombok.Data;

/**
 * @author dhx
 * @date 2025/1/10 16:35
 */
@Data
public class UpdatePostDTO {
    private Long postId;
    private String img;
    private String context;
}