package com.core.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
    自动填充处理器：创建时间、更新时间
    官网有：https://baomidou.com/pages/4c6bcf/
*/
@Slf4j
@Component// 加入IOC容器中
public class MyMetaObjectHandler implements MetaObjectHandler {
    // 插入时填充策略
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.setFieldValByName("create_time", new Date(), metaObject);
        this.setFieldValByName("update_time", new Date(), metaObject);
    }
    // 更新时的填充策略
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.setFieldValByName("update_time", new Date(), metaObject);
    }
}
