package com.hx.entity.DTO.comment;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dhx
 * @date 2025/2/27 21:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentId;
    private String content;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentRootId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    private String userNickName;
    private String userAvatar;
}
