package com.stars.controller.shoppingMall;

import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.OmsCompanyAddressMapper;
import com.stars.pojo.shoppingMall.OmsCompanyAddress;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "OmsCompanyAddressController", description = "收货地址管理")
@RequestMapping("/companyAddress")
@CrossOrigin
public class OmsCompanyAddressController {
    @Autowired
    private OmsCompanyAddressMapper omsCompanyAddressMapper;

    @ApiOperation("获取所有收货地址")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData companyAddressList() {
        List<OmsCompanyAddress> omsCompanyAddresses = omsCompanyAddressMapper.selectWay();
        return ResponseDataUtil.buildSuccess(omsCompanyAddresses);
    }
}
