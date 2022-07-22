package com.core.mapper;

import com.core.pojo.OmsCompanyAddress;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface OmsCompanyAddressMapper {
    List<OmsCompanyAddress> selectWay();
}
