package com.stars.mapper.shoppingMall;

import com.stars.pojo.news.LoginParams;
import com.stars.pojo.shoppingMall.UmsAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface UmsAdminMapper {
    List<UmsAdmin> selectWay(@Param("username") String username);
    // 根据用户名查寻一条用户数据
    UmsAdmin selectRow(@Param("username") String username);
    int insertWay(UmsAdmin record);
    int updateWay(UmsAdmin record);
    int deleteWay(@Param("id") Long id);
    /**
     * 登录验证
     * 报错There is no getter for property named 'keyword' in 'class java.lang.String'
     * 报错Parameter 'username' not found. Available parameters are [arg1, arg0, param1, param2]
     * https://blog.csdn.net/www_11112/article/details/118662096
     * 解决：加@Param
     * @param username
     * @param password
     * @return
     */
    LoginParams loginVerify(@Param("username") String username, @Param("password") String password);
}
