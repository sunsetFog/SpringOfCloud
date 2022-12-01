package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.SmsHomeRecommendSubject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface SmsHomeRecommendSubjectMapper {
    List<SmsHomeRecommendSubject> selectWay(String subjectName, Integer recommendStatus);
    int insertWay(SmsHomeRecommendSubject smsHomeRecommendSubject);
    int updateWay(SmsHomeRecommendSubject smsHomeRecommendSubject);
    int deleteWay(List<Long> ids);
}
