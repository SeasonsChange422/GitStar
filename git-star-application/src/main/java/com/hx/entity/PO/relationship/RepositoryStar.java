package com.hx.entity.PO.relationship;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dhx
 * @date 2025/5/8 15:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("gitstar_repository_star")
public class RepositoryStar {
    @TableId
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long repositoryId;

    public RepositoryStar(Long userId, Long repositoryId) {
        this.userId = userId;
        this.repositoryId = repositoryId;
    }
}
