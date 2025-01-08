package com.hx.entity.PO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author dhx
 * @date 2025/1/8 16:08
 */
@TableName("gitstar_comment")
@Data
public class Comment {
    @TableId
    private Long id;
    @TableField("comment_rootId")
    private Long rootId;
    @TableField("comment_text")
    private Long text;
}
