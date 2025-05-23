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

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author dhx
 * @date 2025/1/8 16:06
 */
@TableName("gitstar_project")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId("project_id")
    private Long id;
    @TableField("project_name")
    private String name;
    @TableField("project_visibility")
    private Boolean visibility;
    @TableField("project_logo")
    private String logo;
    @TableField("project_description")
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private Date createTime;
    private static final long serialVersionUID = 1L;
    public Project(String name, Boolean visibility,String logo,String description) {
        this.name = name;
        this.visibility = visibility;
        this.logo = logo;
        this.description = description;
    }
}
