package com.hx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.entity.DTO.notice.DelNoticeDTO;
import com.hx.entity.DTO.notice.NewNoticeDTO;
import com.hx.entity.PO.Notice;
import com.hx.mapper.NoticeMapper;
import com.hx.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dhx
 * @date 2025/2/28 10:21
 */
@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;
    @Override
    public Result newNotice(NewNoticeDTO newNoticeDTO) {
        Notice notice = new Notice(newNoticeDTO.getText(), newNoticeDTO.getImg());
        if(noticeMapper.insert(notice)==1){
            return Result.okResult(notice);
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result delNotice(DelNoticeDTO delNoticeDTO) {
        if(noticeMapper.deleteById(delNoticeDTO.getId())==1){
            return Result.okResult(200,"删除成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result getNoticeList() {
        List<Notice> list =noticeMapper.selectList(new QueryWrapper<Notice>().or(true));
//        List<Notice> list = noticeMapper.selectAll();
        return Result.okResult(list);
    }
}
