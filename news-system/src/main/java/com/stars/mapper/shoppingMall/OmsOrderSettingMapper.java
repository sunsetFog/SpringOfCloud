package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.OmsOrderSetting;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface OmsOrderSettingMapper {
    OmsOrderSetting selectById(Long id);
    int updateWay(OmsOrderSetting omsOrderSetting);
}
