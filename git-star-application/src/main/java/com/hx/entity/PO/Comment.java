package com.hx.entity.PO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author dhx
 * @date 2025/1/8 16:08
 */
@TableName("gitstar_comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId("comment_id")
    private Long id;
    @TableField("comment_rootId")
    private Long rootId;
    @TableField("comment_text")
    private String text;
    @TableField(value = "createTime",fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;
    private static final long serialVersionUID = 1L;

    public Comment(Long id) {
        this.id = id;
    }

    public Comment(Long rootId, String text) {
        this.rootId = rootId;
        this.text = text;
    }
}
