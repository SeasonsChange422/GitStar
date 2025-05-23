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
 * @date 2025/5/10 16:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("gitstar_developer_request")
public class JoinRequest implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId("request_id")
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;

    public JoinRequest(Long userId, Long projectId) {
        this.userId = userId;
        this.projectId = projectId;
    }
}
