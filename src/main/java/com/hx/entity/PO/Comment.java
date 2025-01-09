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
@TableName("gitstar_comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @TableId("comment_id")
    private Long id;
    @TableField("comment_rootId")
    private Long rootId;
    @TableField("comment_text")
    private Long text;
    private static final long serialVersionUID = 1L;

    public Comment(Long rootId, Long text) {
        this.rootId = rootId;
        this.text = text;
    }
}
