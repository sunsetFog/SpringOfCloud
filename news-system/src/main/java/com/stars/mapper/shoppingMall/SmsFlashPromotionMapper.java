package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.SmsFlashPromotion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface SmsFlashPromotionMapper {
    List<SmsFlashPromotion> selectWay(@Param("keyword") String keyword);
    int insertWay(SmsFlashPromotion smsFlashPromotion);
    int updateWay(SmsFlashPromotion smsFlashPromotion);
    int deleteWay(@Param("id") Long id);
}
