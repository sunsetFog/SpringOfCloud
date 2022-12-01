package com.stars.controller.shoppingMall;

import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.UmsMemberLevelMapper;
import com.stars.pojo.shoppingMall.UmsMemberLevel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "UmsMemberLevelController", description = "会员等级管理")
@RequestMapping("/memberLevel")
@CrossOrigin
public class UmsMemberLevelController {
    @Autowired
    private UmsMemberLevelMapper umsMemberLevelMapper;

    @ApiOperation("查询所有会员等级")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData memberLevelList(@RequestParam("defaultStatus") Integer defaultStatus) {
        List<UmsMemberLevel> umsMemberLevels = umsMemberLevelMapper.selectWay(defaultStatus);
        return ResponseDataUtil.buildSuccess(umsMemberLevels);
    }
}
