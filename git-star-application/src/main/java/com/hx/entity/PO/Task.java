package com.hx.entity.PO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hx.enums.TaskCloseTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dhx
 * @date 2025/5/15 17:39
 */

@TableName("gitstar_task")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId("task_id")
    private Long id;
    @TableField("task_title")
    private String title;
    @TableField(value = "createTime",fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;
    @TableField("closed")
    private Boolean closed;
    @TableField("close_type")
    private TaskCloseTypeEnum type;
    private static final long serialVersionUID = 1L;
    public Task(String title) {
        this.title = title;
    }

    public Task(Long id, String title, Boolean closed, TaskCloseTypeEnum type) {
        this.id = id;
        this.title = title;
        this.closed = closed;
        this.type = type;
    }
}
