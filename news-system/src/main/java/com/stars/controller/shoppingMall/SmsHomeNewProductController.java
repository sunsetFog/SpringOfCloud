package com.stars.controller.shoppingMall;

import com.stars.common.util.PageResult;
import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.SmsHomeNewProductMapper;
import com.stars.pojo.shoppingMall.SmsHomeNewProduct;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "SmsHomeNewProductController", description = "首页新品管理")
@RequestMapping("/homeNewProduct")
@CrossOrigin
public class SmsHomeNewProductController {
    @Autowired
    private SmsHomeNewProductMapper smsHomeNewProductMapper;

    @ApiOperation("分页查询首页新品")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData homeNewProductList(@RequestParam(value = "productName", required = false) String productName,
                                           @RequestParam(value = "recommendStatus", required = false) Integer recommendStatus,
                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SmsHomeNewProduct> smsHomeNewProducts = smsHomeNewProductMapper.selectWay(productName, recommendStatus);
        // 分页
        PageInfo<SmsHomeNewProduct> pageInfo = new PageInfo<SmsHomeNewProduct>(smsHomeNewProducts);
        PageResult pageResult = PageResult.getPageResult(pageInfo);
        return ResponseDataUtil.buildSuccess(pageResult);
    }
    /*
        传参：
            [
                {
                    "productId": 26,
                    "productName": "二哈哈"
                }
            ]
    */
    @ApiOperation("添加首页新品")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData homeNewProductAdd(@RequestBody List<SmsHomeNewProduct> homeNewProductList) {
        for (SmsHomeNewProduct item: homeNewProductList) {
            item.setRecommendStatus(1);
            item.setSort(0);
            smsHomeNewProductMapper.insertWay(item);
        }
        return ResponseDataUtil.countJudge(homeNewProductList.size());
    }
    @ApiOperation("修改首页新品")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData homeNewProductUpdate(@RequestBody SmsHomeNewProduct homeNewProductList) {
        int count = smsHomeNewProductMapper.updateWay(homeNewProductList);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("批量删除首页新品")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseData homeNewProductDelete(@RequestParam("ids") List<Long> ids) {
        smsHomeNewProductMapper.deleteWay(ids);
        return ResponseDataUtil.countJudge(ids.size());
    }
}
