package com.hx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.entity.DTO.history.NewHistory;
import com.hx.entity.PO.History;
import com.hx.entity.PO.relationship.TaskHistory;
import com.hx.mapper.HistoryMapper;
import com.hx.mapper.relationship.TaskHistoryMapper;
import com.hx.service.HistoryService;
import com.hx.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dhx
 * @date 2025/5/15 23:34
 */
@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private TaskHistoryMapper taskHistoryMapper;
    @Override
    public Result newHistory(NewHistory newHistory,String token) {
        try {
            Long userId = JwtUtil.getUserIdFromToken(token);
            History history = new History(userId,newHistory.getContent());
            historyMapper.insert(history);
            taskHistoryMapper.insert(new TaskHistory(newHistory.getTaskId(), history.getId()));
            return Result.okResult();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getHistoryOfTask(Long taskId) {
        try{
            List<Long> historyIds = taskHistoryMapper.selectList(new QueryWrapper<TaskHistory>().eq("task_id",taskId))
                    .stream().map(TaskHistory::getHistoryId).collect(Collectors.toList());
            if(historyIds.isEmpty()){
                return Result.okResult(new ArrayList<History>());
            }
            List<History> histories = historyMapper.selectBatchIds(historyIds);
            return Result.okResult(histories);
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }
}
