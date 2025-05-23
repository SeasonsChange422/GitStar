package com.hx.entity.PO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author dhx
 * @date 2025/5/10 10:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("gitstar_tag")
public class Tag implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId("gtag_id")
    private Long id;
    @TableField("gtag_name")
    private String name;
    private static final long serialVersionUID = 1L;

    public Tag(String name) {
        this.name = name;
    }
}
