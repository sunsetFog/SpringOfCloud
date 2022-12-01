package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.SmsCouponHistory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface SmsCouponHistoryMapper {
    List<SmsCouponHistory> selectWay(Long couponId, Integer useStatus, String orderSn);
}
