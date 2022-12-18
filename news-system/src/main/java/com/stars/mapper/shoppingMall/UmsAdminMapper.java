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
    List<UmsAdmin> selectWay(String username);
    int insertWay(UmsAdmin record);
    int updateWay(UmsAdmin record);
    int deleteWay(Long id);
    /**
     * 登录验证
     * 报错Parameter 'username' not found. Available parameters are [arg1, arg0, param1, param2]
     * https://blog.csdn.net/www_11112/article/details/118662096
     * 解决：加@Param
     * @param username
     * @param password
     * @return
     */
    LoginParams loginVerify(@Param("username") String username, @Param("password") String password);
}
