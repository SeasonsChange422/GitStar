package com.hx.entity.PO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dhx
 * @date 2025/2/28 10:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("gitstar_notice")
public class Notice implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId("notice_id")
    private Long id;
    @TableField("notice_text")
    private String text;
    @TableField("notice_img")
    private String img;
    @TableField(value = "createTime",fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;
    private static final long serialVersionUID = 1L;

    public Notice(String text, String img) {
        this.text = text;
        this.img = img;
    }
}
