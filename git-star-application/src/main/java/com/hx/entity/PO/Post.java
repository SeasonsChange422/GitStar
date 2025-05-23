package com.hx.entity.PO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import com.hx.entity.DTO.post.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dhx
 * @date 2025/1/8 16:08
 */
@TableName("gitstar_post")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId("post_id")
    private Long id;
    @TableField("post_img")
    private String img;
    @TableField("post_context")
    private String context;
    @TableField("post_title")
    private String title;
    @TableField(value = "createTime",fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;
    private static final long serialVersionUID = 1L;

    public Post(String title,String img, String context) {
        this.title = title;
        this.img = img;
        this.context = context;
    }
    public Post(String title,Long id,String img, String context) {
        this.title = title;
        this.id = id;
        this.img = img;
        this.context = context;
    }
    public Post(Post post){
        this.id = post.getId();
        this.img = post.getImg();
        this.context = post.getContext();
        this.title = post.getTitle();
        this.createTime = post.getCreateTime();
    }
}
