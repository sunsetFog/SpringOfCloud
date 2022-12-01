package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.UmsAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface UmsAdminMapper {
    List<UmsAdmin> selectWay(String username);
    int insertWay(UmsAdmin record);
    int updateWay(UmsAdmin record);
    int deleteWay(Long id);
}
