package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.PmsProductFullReduction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface PmsProductFullReductionMapper {
    int deleteWay(Long productId);
    int insertList(@Param("list") List<PmsProductFullReduction> productFullReductionList);
}
