package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.SmsCouponHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface SmsCouponHistoryMapper {
    List<SmsCouponHistory> selectWay(@Param("couponId") Long couponId, @Param("useStatus") Integer useStatus, @Param("orderSn") String orderSn);
}
