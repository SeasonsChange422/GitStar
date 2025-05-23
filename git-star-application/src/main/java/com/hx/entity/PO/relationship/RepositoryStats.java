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
 * @date 2025/5/1 21:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("gitstar_repository_stats")
public class RepositoryStats {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId
    @TableField("repository_id")
    private Long repositoryId;
    @TableField("download")
    private int download;
    @TableField("view")
    private int view;
    @TableField("star")
    private int star;

}
