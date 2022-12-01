package com.stars.mapper;

import com.stars.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
// 实体类
@Mapper // 扫描文件 mybatis的mapper类
@Repository// 或者@component  注入spring里
public interface GoodsMapper {
    // 分页查询
    List<Goods> goodsQueryList(String name);
    // 上图片改字段
    int uploadGoods(Goods record);
    // 添加
    int insertSelective(Goods record);
    // 查询指定单条数据
    Goods selectByPrimaryKey(Integer id);
    // 删除
    int deleteGoods(Integer id);
    // 编辑修改
    int updateGoods(Goods record);
}
