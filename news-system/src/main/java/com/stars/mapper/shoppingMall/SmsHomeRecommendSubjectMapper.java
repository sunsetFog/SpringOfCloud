package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.SmsHomeRecommendSubject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface SmsHomeRecommendSubjectMapper {
    List<SmsHomeRecommendSubject> selectWay(@Param("subjectName") String subjectName, @Param("recommendStatus") Integer recommendStatus);
    int insertWay(SmsHomeRecommendSubject smsHomeRecommendSubject);
    int updateWay(SmsHomeRecommendSubject smsHomeRecommendSubject);
    int deleteWay(@Param("ids") List<Long> ids);
}
