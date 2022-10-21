package com.core.service.impl;

import com.core.common.api.ISysBaseAPI;
import com.core.pojo.Goods;
import com.core.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysBaseApiImpl implements ISysBaseAPI {
    @Autowired
    private GoodsService goodsService;

    @Override
    public Goods goodsList(Integer id) {
        Goods goods = goodsService.fishWay(id);
        return goods;
    }
}
