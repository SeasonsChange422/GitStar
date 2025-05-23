package com.hx.service;

import com.hx.common.Result;
import com.hx.entity.DTO.feedback.NewFeedbackDTO;

/**
 * @author dhx
 * @date 2025/5/11 11:36
 */
public interface FeedbackService {
    public Result newFeedback(NewFeedbackDTO newFeedbackDTO,String token);
    public Result readFeedback(Long feedbackId);
    public Result getFeedbackList();
    public Result getFeedbackListByType(String type);
}
