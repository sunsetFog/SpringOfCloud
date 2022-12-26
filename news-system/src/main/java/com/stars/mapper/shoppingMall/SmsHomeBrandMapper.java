package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.SmsHomeBrand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface SmsHomeBrandMapper {
    List<SmsHomeBrand> selectWay(@Param("brandName") String brandName, @Param("recommendStatus") Integer recommendStatus);
    int insertWay(SmsHomeBrand smsHomeBrand);
    int updateWay(SmsHomeBrand smsHomeBrand);
    int deleteWay(@Param("ids") List<Long> ids);
}
