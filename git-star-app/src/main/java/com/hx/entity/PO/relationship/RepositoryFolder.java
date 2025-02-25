package com.hx.entity.PO.relationship;

import com.baomidou.mybatisplus.annotation.TableName;
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
    private Long repositoryId;
    private Long folderId;
}
