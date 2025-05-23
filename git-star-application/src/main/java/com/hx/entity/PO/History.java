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
 * @date 2025/5/15 17:39
 */
@TableName("gitstar_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class History implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId("history_id")
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("user_id")
    private Long userId;
    @TableField("history_content")
    private String content;
    @TableField(value = "createTime",fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;
    private static final long serialVersionUID = 1L;
    public History(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }
}
