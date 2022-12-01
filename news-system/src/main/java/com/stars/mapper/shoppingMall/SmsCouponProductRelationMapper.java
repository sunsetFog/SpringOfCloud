package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.SmsCouponProductRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface SmsCouponProductRelationMapper {
    /**
     * 批量创建
     */
    int insertList(@Param("list") List<SmsCouponProductRelation> productRelationList);
    int deleteWay(Long id);
}
