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
 * @date 2025/5/9 19:51
 */
@TableName("gitstar_release")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Release implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId("release_id")
    private Long id;
    @TableField("release_file")
    private String file;
    @TableField("release_description")
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private Date createTime;
    private static final long serialVersionUID = 1L;

    public Release(String file, String description) {
        this.file = file;
        this.description = description;
    }
}
