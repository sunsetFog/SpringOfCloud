package com.stars.mapper.news;

import com.stars.pojo.news.LoginParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    /**
     * 报错Parameter 'username' not found. Available parameters are [arg1, arg0, param1, param2]
     * https://blog.csdn.net/www_11112/article/details/118662096
     * 解决：加@Param
     * @param username
     * @param password
     * @return
     */
    LoginParams loginVerify(@Param("username") String username, @Param("password") String password);
}
