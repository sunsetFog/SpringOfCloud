package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.UmsMemberLevel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface UmsMemberLevelMapper {
    List<UmsMemberLevel> selectWay(Integer defaultStatus);
}
