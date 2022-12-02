package com.core.mapper;

import org.mapstruct.Mapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Repository;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
@FeignClient(value = "eureka-client")
public interface FeignMapper {
//    String selectWay();
}
