package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.OmsCompanyAddress;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface OmsCompanyAddressMapper {
    List<OmsCompanyAddress> selectWay();
}
