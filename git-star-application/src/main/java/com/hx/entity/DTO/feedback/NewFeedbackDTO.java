package com.hx.entity.DTO.feedback;

import lombok.Data;

/**
 * @author dhx
 * @date 2025/5/11 11:38
 */
@Data
public class NewFeedbackDTO {
    private String feedback;
    private Long objectId;
    private String type;
}
