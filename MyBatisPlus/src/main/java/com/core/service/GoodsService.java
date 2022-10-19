package com.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.core.pojo.Goods;

public interface GoodsService extends IService<Goods> {
    public Goods fishWay(Integer id);
}
