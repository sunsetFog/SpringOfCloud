package com.stars.mapper.shoppingMall;

import com.stars.apiParams.PmsProductAddParam;
import com.stars.apiParams.PmsProductListParam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface PmsProductMapper {
    List<PmsProductAddParam> selectWay(PmsProductListParam productQueryParam);
    int insertWay(PmsProductAddParam pmsProductAddParam);
    int updateWay(PmsProductAddParam pmsProductAddParam);
    int update_deleteStatus(Long id, Integer deleteStatus);
}
