package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.UmsMenu;
import com.stars.pojo.shoppingMall.UmsRoleMenuRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface UmsRoleMenuRelationMapper {
    /**
     * 根据角色ID获取菜单
     */
    List<UmsMenu> selectWay(Long roleId);
    int deleteWay(Long roleId);
    int insertList(UmsRoleMenuRelation umsRoleMenuRelation);
}
