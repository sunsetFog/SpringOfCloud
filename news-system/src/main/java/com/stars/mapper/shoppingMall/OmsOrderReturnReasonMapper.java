package com.stars.mapper.shoppingMall;

import com.stars.pojo.shoppingMall.OmsOrderReturnReason;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface OmsOrderReturnReasonMapper {
    List<OmsOrderReturnReason> selectWay();
    int insertWay(OmsOrderReturnReason omsOrderReturnReason);
    int updateWay(OmsOrderReturnReason omsOrderReturnReason);
    int deleteWay(Long id);
}
