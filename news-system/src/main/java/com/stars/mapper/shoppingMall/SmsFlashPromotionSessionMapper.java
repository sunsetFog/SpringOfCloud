package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.SmsFlashPromotionSession;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface SmsFlashPromotionSessionMapper {
    List<SmsFlashPromotionSession> selectWay(@Param("status") Integer status);
    int insertWay(SmsFlashPromotionSession smsFlashPromotionSession);
    int updateWay(SmsFlashPromotionSession smsFlashPromotionSession);
    int deleteWay(@Param("id") Long id);
}
