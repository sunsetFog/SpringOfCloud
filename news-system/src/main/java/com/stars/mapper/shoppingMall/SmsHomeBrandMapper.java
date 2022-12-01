package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.SmsHomeBrand;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface SmsHomeBrandMapper {
    List<SmsHomeBrand> selectWay(String brandName, Integer recommendStatus);
    int insertWay(SmsHomeBrand smsHomeBrand);
    int updateWay(SmsHomeBrand smsHomeBrand);
    int deleteWay(List<Long> ids);
}
