package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.PmsProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface PmsProductCategoryMapper {
    List<PmsProductCategory> selectWay(Long parentId);
    PmsProductCategory selectIdWay(Long parentId);
    int insertWay(PmsProductCategory pmsProductCategory);
    int updateWay(PmsProductCategory pmsProductCategory);
    int deleteWay(Long id);
    int statusWay(Long id, Integer navStatus, Integer showStatus);
}
