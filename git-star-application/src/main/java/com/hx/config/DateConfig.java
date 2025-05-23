package com.hx.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.time.LocalDateTime;

/**
 * @author dhx
 * @date 2025/2/27 1:12
 */
@Component
public class DateConfig implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = new Date();
        long time = now.getTime();
        time = time / 60000 * 60000;
        now.setTime(time);
        this.setFieldValByName("createTime", now, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
