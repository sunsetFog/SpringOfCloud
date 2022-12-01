package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.PmsProductCategoryAttributeRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface PmsProductCategoryAttributeRelationMapper {
    List<PmsProductCategoryAttributeRelation> selectWay(Long productCategoryId);
    int deleteWay(Long productCategoryId);
    int insertList(@Param("list") List<PmsProductCategoryAttributeRelation> productCategoryAttributeRelationList);
}
