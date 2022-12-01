package com.stars.mapper.shoppingMall;

import com.stars.apiParams.OmsOrderReturnApplyListParam;
import com.stars.pojo.shoppingMall.OmsOrderReturnApply;
import com.stars.pojo.shoppingMall.OmsOrderReturnApplyResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 扫描文件 mybatis的mapper类
@Repository
public interface OmsOrderReturnApplyMapper {
    /**
     * 查询申请列表
     */
    List<OmsOrderReturnApply> selectWay(OmsOrderReturnApplyListParam omsOrderReturnApplyListParam);
    /**
     * 获取申请详情
     */
    OmsOrderReturnApplyResult selectById(Long id);
    int updateWay(OmsOrderReturnApply omsOrderReturnApply);
}
