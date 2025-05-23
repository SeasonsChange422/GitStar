package com.hx.entity.PO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hx.enums.FeedbackTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author dhx
 * @date 2025/5/11 11:32
 */
@TableName("gitstar_feedback")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId("id")
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("object_id")
    private Long objectId;
    @TableField("feedback_content")
    private String feedback;
    @TableField("feedback_type")
    private FeedbackTypeEnum type;
    @TableField("solved")
    private Boolean solved;
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("user_id")
    private Long userId;

    public Feedback(String feedback, Long userId) {
        this.feedback = feedback;
        this.userId = userId;
    }
    public Feedback(String feedback,FeedbackTypeEnum type, Long userId) {
        this.feedback = feedback;
        this.type = type;
        this.userId = userId;
    }
    public Feedback(Long objectId,String feedback,FeedbackTypeEnum type, Long userId) {
        this.objectId = objectId;
        this.feedback = feedback;
        this.type = type;
        this.userId = userId;
    }
}
