package com.hx.entity.PO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dhx
 * @date 2025/1/8 16:08
 */
@TableName("gitstar_repository")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Repository {
    @TableId("repository_id")
    private Long id;
    @TableField("repository_name")
    private String name;
    private static final long serialVersionUID = 1L;

    public Repository(String name) {
        this.name = name;
    }
}
