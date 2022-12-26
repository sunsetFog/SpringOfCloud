package com.stars.mapper.shoppingMall;

import com.stars.apiParams.SmsCouponAddParam;
import com.stars.pojo.shoppingMall.SmsCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface SmsCouponMapper {
    List<SmsCouponAddParam> selectWay(@Param("name") String name, @Param("type") Integer type);
    SmsCouponAddParam selectRow(@Param("id") Long id);
    int insertWay(SmsCoupon smsCoupon);
    int updateWay(SmsCoupon smsCoupon);
    int deleteWay(@Param("id") Long id);
}
