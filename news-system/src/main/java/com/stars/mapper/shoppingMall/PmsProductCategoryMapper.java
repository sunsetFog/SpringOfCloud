package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.PmsProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface PmsProductCategoryMapper {
    List<PmsProductCategory> selectWay(@Param("parentId") Long parentId);
    PmsProductCategory selectIdWay(@Param("parentId") Long parentId);
    int insertWay(PmsProductCategory pmsProductCategory);
    int updateWay(PmsProductCategory pmsProductCategory);
    int deleteWay(@Param("id") Long id);
    int statusWay(@Param("id") Long id, @Param("navStatus") Integer navStatus, @Param("showStatus") Integer showStatus);
}
