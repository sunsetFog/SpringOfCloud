package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.UmsMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface UmsMenuMapper {
    /**
     * 根据用户查询用户权限
     * @param username
     * @return
     */
    Set<String> queryUserAuths(@Param("username") String username);
    List<UmsMenu> btnWay();
    List<UmsMenu> selectWay(@Param("subject") String subject);
    UmsMenu rowWay(@Param("menuParentId") Long menuParentId);
    int insertWay(UmsMenu umsMenu);
    int updateWay(UmsMenu umsMenu);
    int deleteWay(@Param("ids") List<Long> ids);
}
