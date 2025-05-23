package com.hx.controller;

import com.hx.common.Result;
import com.hx.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dhx
 * @date 2025/2/28 10:35
 */
@RestController
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @GetMapping("/api/notice/getNoticeList")
    public Result getNoticeList(){
        return noticeService.getNoticeList();
    }
}
