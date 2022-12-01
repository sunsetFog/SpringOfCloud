package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.UmsResourceCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface UmsResourceCategoryMapper {
    List<UmsResourceCategory> selectWay();
    int insertWay(UmsResourceCategory umsResourceCategory);
    int updateWay(UmsResourceCategory umsResourceCategory);
    int deleteWay(Long id);
}
