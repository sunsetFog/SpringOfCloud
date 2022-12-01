package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.PmsMemberPrice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface PmsMemberPriceMapper {
    int deleteWay(Long productId);
    int insertList(@Param("list") List<PmsMemberPrice> memberPriceList);
}
