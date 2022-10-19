package com.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.core.mapper.GoodsMapper;
import com.core.pojo.Goods;
import com.core.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    @Transactional(rollbackFor = Exception.class) // 事务:抛出异常，就会回滚
    public Goods fishWay(Integer id) {
        Goods goods = this.goodsMapper.selectByPrimaryKey(id);
        return goods;
    }
}
