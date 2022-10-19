package com.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.core.pojo.Goods;
import org.springframework.stereotype.Repository;

/*
继承基本的接口，就有了父类的方法
*/
@Repository // 代表持久层
public interface GoodsMapper extends BaseMapper<Goods> {
    // 查询指定单条数据
    Goods selectByPrimaryKey(Integer id);
}
