package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.CmsSubject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface CmsSubjectMapper {
    List<CmsSubject> selectWay(@Param("keyword") String keyword);
}
