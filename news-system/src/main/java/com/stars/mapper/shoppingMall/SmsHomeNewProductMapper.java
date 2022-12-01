package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.SmsHomeNewProduct;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface SmsHomeNewProductMapper {
    List<SmsHomeNewProduct> selectWay(String productName, Integer recommendStatus);
    int insertWay(SmsHomeNewProduct smsHomeNewProduct);
    int updateWay(SmsHomeNewProduct smsHomeNewProduct);
    int deleteWay(List<Long> ids);
}
