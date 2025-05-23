package com.hx.controller;

import com.hx.aop.annotation.AdminAuth;
import com.hx.aop.annotation.UserAuth;
import com.hx.common.Result;
import com.hx.entity.DTO.feedback.NewFeedbackDTO;
import com.hx.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dhx
 * @date 2025/5/11 11:51
 */
@RestController
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @UserAuth
    @PostMapping("/api/feedback/newFeedback")
    public Result newFeedback(@RequestBody NewFeedbackDTO newFeedbackDTO, @RequestHeader("token")String token){
        return feedbackService.newFeedback(newFeedbackDTO,token);
    }

    @UserAuth
    @AdminAuth
    @PostMapping("/api/feedback/readFeedback")
    public Result readFeedback(@RequestParam Long feedbackId){
        return feedbackService.readFeedback(feedbackId);
    }

    @UserAuth
    @AdminAuth
    @GetMapping("/api/feedback/getFeedbackList")
    public Result getFeedbackList(){
        return feedbackService.getFeedbackList();
    }

    @UserAuth
    @AdminAuth
    @GetMapping("/api/feedback/getFeedbackListByType")
    public Result getFeedbackListByType(@RequestParam String type) {
        return feedbackService.getFeedbackListByType(type);
    }
}
