package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.UmsAdminRoleRelation;
import com.stars.pojo.shoppingMall.UmsRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface UmsAdminRoleRelationMapper {
    int deleteWay(Long admin_id);
    /**
     * 批量插入用户角色关系
     */
    int insertList(@Param("list") List<UmsAdminRoleRelation> adminRoleRelationList);
    /**
     * 获取用于所有角色
     */
    List<UmsRole> selectWay(@Param("adminId") Long adminId);
}
