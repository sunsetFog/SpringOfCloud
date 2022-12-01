package com.stars.controller.shoppingMall;

import com.stars.apiParams.OmsOrderReturnApplyListParam;
import com.stars.apiParams.OmsOrderReturnApplyStatusParam;
import com.stars.common.util.PageResult;
import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.OmsOrderReturnApplyMapper;
import com.stars.pojo.shoppingMall.OmsOrderReturnApply;
import com.stars.pojo.shoppingMall.OmsOrderReturnApplyResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "OmsOrderReturnApplyController", description = "订单退货申请管理")
@RequestMapping("/returnApply")
@CrossOrigin
public class OmsOrderReturnApplyController {
    @Autowired
    private OmsOrderReturnApplyMapper omsOrderReturnApplyMapper;
    /*
        传参：
        {
            "id": 3,
            "status": 0,
            "createTime": "2018-10-17",
            "handleMan": null,
            "handleTime": null,
            "pageNum": 1,
            "pageSize": 10
        }
    */
    @ApiOperation("分页查询退货申请")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseData returnApplyList(@RequestBody OmsOrderReturnApplyListParam omsOrderReturnApplyListParam) {
        PageHelper.startPage(omsOrderReturnApplyListParam.getPageNum(), omsOrderReturnApplyListParam.getPageSize());
        List<OmsOrderReturnApply> omsOrderReturnApplies = omsOrderReturnApplyMapper.selectWay(omsOrderReturnApplyListParam);
        // 分页
        PageInfo<OmsOrderReturnApply> pageInfo = new PageInfo<OmsOrderReturnApply>(omsOrderReturnApplies);
        PageResult pageResult = PageResult.getPageResult(pageInfo);
        return ResponseDataUtil.buildSuccess(pageResult);
    }

    @ApiOperation("获取退货申请详情")
    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public ResponseData returnApplyDetails(@PathVariable Long id) {
        OmsOrderReturnApplyResult omsOrderReturnApplyResult = omsOrderReturnApplyMapper.selectById(id);
        return ResponseDataUtil.buildSuccess(omsOrderReturnApplyResult);
    }
    /*
        传参：
        {
            "id": 3,
            "companyAddressId": 1,
            "handleMan": "admin",
            "handleNote": null,
            "receiveMan": "admin",
            "receiveNote": null,
            "returnAmount": 0,
            "status": 1
        }
    */
    @ApiOperation("修改退货申请状态")
    @RequestMapping(value = "/update/status", method = RequestMethod.POST)
    public ResponseData returnApplyStatus(@RequestBody OmsOrderReturnApplyStatusParam statusParam) {
        Integer status = statusParam.getStatus();
        OmsOrderReturnApply returnApply = new OmsOrderReturnApply();
        if (status.equals(1)) {
            //确认退货
            returnApply.setId(statusParam.getId());
            returnApply.setStatus(1);
            returnApply.setReturnAmount(statusParam.getReturnAmount());
            returnApply.setCompanyAddressId(statusParam.getCompanyAddressId());
            returnApply.setHandleTime(new Date());
            returnApply.setHandleMan(statusParam.getHandleMan());
            returnApply.setHandleNote(statusParam.getHandleNote());
        } else if (status.equals(2)) {
            //完成退货
            returnApply.setId(statusParam.getId());
            returnApply.setStatus(2);
            returnApply.setReceiveTime(new Date());
            returnApply.setReceiveMan(statusParam.getReceiveMan());
            returnApply.setReceiveNote(statusParam.getReceiveNote());
        } else if (status.equals(3)) {
            //拒绝退货
            returnApply.setId(statusParam.getId());
            returnApply.setStatus(3);
            returnApply.setHandleTime(new Date());
            returnApply.setHandleMan(statusParam.getHandleMan());
            returnApply.setHandleNote(statusParam.getHandleNote());
        } else {
            return ResponseDataUtil.countJudge(0);
        }
        int count = omsOrderReturnApplyMapper.updateWay(returnApply);
        return ResponseDataUtil.countJudge(count);
    }
}
