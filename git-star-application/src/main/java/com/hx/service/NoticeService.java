package com.hx.service;

import com.hx.common.Result;
import com.hx.entity.DTO.notice.DelNoticeDTO;
import com.hx.entity.DTO.notice.NewNoticeDTO;

/**
 * @author dhx
 * @date 2025/2/28 10:21
 */
public interface NoticeService {
    public Result newNotice(NewNoticeDTO newNoticeDTO);

    public Result delNotice(DelNoticeDTO delNoticeDTO);

    public Result getNoticeList();
}
