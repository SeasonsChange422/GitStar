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
@TableName("gitstar_repository")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Repository {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId("repository_id")
    private Long id;
    @TableField("repository_name")
    private String name;
    @TableField(value = "createTime",fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;
    @TableField("repository_description")
    private String description;

    public Repository(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Repository(Repository repository) {
        this.id = repository.getId();
        this.name = repository.getName();
        this.createTime = repository.getCreateTime();
        this.description = repository.getDescription();
    }

    public Repository(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    private static final long serialVersionUID = 1L;
    public Repository(String name) {
        this.name = name;
    }
}
