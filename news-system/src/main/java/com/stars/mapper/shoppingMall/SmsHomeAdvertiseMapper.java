package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.SmsHomeAdvertise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface SmsHomeAdvertiseMapper {
    List<SmsHomeAdvertise> selectWay(@Param("name") String name, @Param("type") Integer type, @Param("endTime") String endTime);
    int insertWay(SmsHomeAdvertise smsHomeAdvertise);
    int updateWay(SmsHomeAdvertise smsHomeAdvertise);
    int deleteWay(@Param("ids") List<Long> ids);
}
