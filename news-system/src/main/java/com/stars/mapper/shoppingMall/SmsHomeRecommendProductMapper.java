package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.SmsHomeRecommendProduct;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface SmsHomeRecommendProductMapper {
    List<SmsHomeRecommendProduct> selectWay(String productName, Integer recommendStatus);
    int insertWay(SmsHomeRecommendProduct smsHomeRecommendProduct);
    int updateWay(SmsHomeRecommendProduct smsHomeRecommendProduct);
    int deleteWay(List<Long> ids);
}
