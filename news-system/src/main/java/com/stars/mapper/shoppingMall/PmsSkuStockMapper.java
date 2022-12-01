package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.PmsSkuStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface PmsSkuStockMapper {
    List<PmsSkuStock> selectWay(Long productId, String keyword);
    int deleteWay(Long productId);
    int insertList(@Param("list") List<PmsSkuStock> skuStockList);
    /**
     * 批量插入或替换操作
     */
    int replaceList(List<PmsSkuStock> skuStockList);
}
