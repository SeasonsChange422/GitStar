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
@TableName("gitstar_post")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @TableId("post_id")
    private Long id;
    @TableField("post_img")
    private String img;
    @TableField("post_context")
    private String context;
    private static final long serialVersionUID = 1L;

    public Post(String img, String context) {
        this.img = img;
        this.context = context;
    }
}
