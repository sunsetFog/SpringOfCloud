package com.stars.controller.shoppingMall;

import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.OmsOrderSettingMapper;
import com.stars.pojo.shoppingMall.OmsOrderSetting;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "OmsOrderSettingController", description = "订单设置管理")
@RequestMapping("/orderSetting")
@CrossOrigin
public class OmsOrderSettingController {
    @Autowired
    private OmsOrderSettingMapper omsOrderSettingMapper;

    @ApiOperation("获取指定订单设置")
    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public ResponseData orderSettingDetails(@PathVariable Long id) {
        OmsOrderSetting omsOrderSetting = omsOrderSettingMapper.selectById(id);
        return ResponseDataUtil.buildSuccess(omsOrderSetting);
    }
    /*
        传参:
            {
                "id": 1,
                "flashOrderOvertime": 60,
                "normalOrderOvertime": 120,
                "confirmOvertime": 15,
                "finishOvertime": 7,
                "commentOvertime": 7
            }
    */
    @ApiOperation("修改指定订单设置")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData orderSettingUpdate(@RequestBody OmsOrderSetting omsOrderSetting) {
        int count = omsOrderSettingMapper.updateWay(omsOrderSetting);
        return ResponseDataUtil.countJudge(count);
    }
}
