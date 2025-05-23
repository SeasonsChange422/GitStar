package com.hx.controller;

import com.hx.aop.annotation.UserAuth;
import com.hx.common.Result;
import com.hx.entity.DTO.history.NewHistory;
import com.hx.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dhx
 * @date 2025/5/16 0:07
 */
@RestController
public class HistoryController {
    @Autowired
    private HistoryService historyService;
    @UserAuth
    @PostMapping("/api/history/newHistory")
    public Result newHistory(@RequestBody NewHistory newHistory,@RequestHeader("token") String token){
        return historyService.newHistory(newHistory,token);
    }

    @UserAuth
    @GetMapping("/api/history/getHistoryOfTask")
    public Result getHistoryOfTask(@RequestParam Long taskId){
        return historyService.getHistoryOfTask(taskId);
    }
}
