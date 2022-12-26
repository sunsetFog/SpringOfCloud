package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.UmsResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface UmsResourceMapper {
    List<UmsResource> selectWay(@Param("categoryId") Long categoryId, @Param("nameKeyword") String nameKeyword, @Param("urlKeyword") String urlKeyword);
    int insertWay(UmsResource umsResource);
    int updateWay(UmsResource umsResource);
    int deleteWay(@Param("id") Long id);
}
