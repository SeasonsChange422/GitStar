package com.hx.entity.PO.relationship;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dhx
 * @date 2025/2/21 16:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("gitstar_repository_folder")
public class RepositoryFolder {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long repositoryId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long folderId;
}
