package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.PmsBrand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface PmsBrandMapper {
    List<PmsBrand> selectWay(@Param("keyword") String keyword);
    int insertWay(PmsBrand pmsBrand);
    int updateWay(PmsBrand pmsBrand);
    int deleteWay(@Param("id") Long id);
}
