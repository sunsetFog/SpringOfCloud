package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.SmsFlashPromotionProduct;
import com.stars.pojo.shoppingMall.SmsFlashPromotionProductRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface SmsFlashPromotionProductRelationMapper {
    long selectCount(Long flashPromotionId, Long flashPromotionSessionId);
    /**
     * 获取限时购及相关商品信息
     */
    List<SmsFlashPromotionProduct> selectWay(Long flashPromotionId, Long flashPromotionSessionId);
    int insertWay(SmsFlashPromotionProductRelation smsFlashPromotionProductRelation);
    int updateWay(SmsFlashPromotionProductRelation smsFlashPromotionProductRelation);
    int deleteWay(Long id);
}
