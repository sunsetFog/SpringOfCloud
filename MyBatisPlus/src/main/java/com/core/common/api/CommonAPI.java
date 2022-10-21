package com.core.common.api;

import com.core.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommonAPI {
    public Goods goodsList(Integer id);
}
