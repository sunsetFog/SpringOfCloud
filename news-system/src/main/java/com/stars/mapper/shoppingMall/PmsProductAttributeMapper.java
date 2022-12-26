package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.PmsProductAttribute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface PmsProductAttributeMapper {
    List<PmsProductAttribute> selectWay(@Param("cid") Long cid, @Param("type") Integer type);
    PmsProductAttribute selectById(@Param("id") Long id);
    int insertWay(PmsProductAttribute pmsProductAttribute);
    int updateWay(PmsProductAttribute pmsProductAttribute);
    int deleteWay(@Param("id") Long id);
}
