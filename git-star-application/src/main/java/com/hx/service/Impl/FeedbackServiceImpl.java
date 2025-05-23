package com.hx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.enums.FeedbackTypeEnum;
import com.hx.entity.DTO.feedback.NewFeedbackDTO;
import com.hx.entity.PO.Feedback;
import com.hx.mapper.FeedbackMapper;
import com.hx.service.FeedbackService;
import com.hx.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dhx
 * @date 2025/5/11 11:37
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;
    @Override
    public Result newFeedback(NewFeedbackDTO newFeedbackDTO,String token) {
        try{
            Long userId = JwtUtil.getUserIdFromToken(token);
            Feedback feedback;
            switch (newFeedbackDTO.getType()){
                case "common": {
                    feedback = new Feedback(newFeedbackDTO.getFeedback(),FeedbackTypeEnum.valueOf(newFeedbackDTO.getType().toUpperCase()),userId);
                    break;
                }
                default: {
                    feedback = new Feedback(newFeedbackDTO.getObjectId(),newFeedbackDTO.getFeedback(),FeedbackTypeEnum.valueOf(newFeedbackDTO.getType().toUpperCase()),userId);
                    break;
                }
            }
            feedbackMapper.insert(feedback);
            return Result.okResult();
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }
    @Override
    public Result readFeedback(Long feedbackId) {
        try{
            Feedback feedback = feedbackMapper.selectById(feedbackId);
            feedback.setSolved(true);
            feedbackMapper.updateById(feedback);
            return Result.okResult();
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getFeedbackList() {
        try{
            List<Feedback> feedbacks = feedbackMapper.selectList(new QueryWrapper<Feedback>().eq("solved",false));
            return Result.okResult(feedbacks);
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getFeedbackListByType(String type) {
        try {
            List<Feedback> feedbacks = feedbackMapper.selectList(new QueryWrapper<Feedback>().eq("solved",false).eq("feedback_type",type));
            return Result.okResult(feedbacks);
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }
}
