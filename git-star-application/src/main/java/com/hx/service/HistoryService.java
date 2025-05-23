package com.hx.service;

import com.hx.common.Result;
import com.hx.entity.DTO.history.NewHistory;

/**
 * @author dhx
 * @date 2025/5/15 23:34
 */
public interface HistoryService {
    public Result newHistory(NewHistory newHistory,String token);

    public Result getHistoryOfTask(Long taskId);
}
