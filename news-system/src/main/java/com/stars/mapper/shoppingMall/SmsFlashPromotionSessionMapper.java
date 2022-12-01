package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.SmsFlashPromotionSession;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface SmsFlashPromotionSessionMapper {
    List<SmsFlashPromotionSession> selectWay(Integer status);
    int insertWay(SmsFlashPromotionSession smsFlashPromotionSession);
    int updateWay(SmsFlashPromotionSession smsFlashPromotionSession);
    int deleteWay(Long id);
}
