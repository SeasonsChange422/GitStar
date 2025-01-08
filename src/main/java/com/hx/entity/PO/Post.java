package com.hx.entity.PO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author dhx
 * @date 2025/1/8 16:08
 */
@TableName("gitstar_post")
@Data
public class Post {
    @TableId
    private Long id;
    @TableField("post_img")
    private String img;
    @TableField("post_context")
    private String context;
}
