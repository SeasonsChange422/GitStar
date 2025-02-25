package com.hx.entity.PO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author dhx
 * @date 2025/2/21 16:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("gitstar_folder")
public class Folder implements Serializable{
    @TableId("folder_id")
    private Long folderId;
    @TableField("folder_name")
    private String folderName;
    private static final long serialVersionUID = 1L;
    public Folder(String folderName) {
        this.folderName = folderName;
    }
}
