package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.LogDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * study: 日志
 */
@Mapper
@Repository
public interface BaseCommonMapper {

    /**
     * 保存日志
     * @param dto
     */
    void saveLog(@Param("dto") LogDTO dto);

}
