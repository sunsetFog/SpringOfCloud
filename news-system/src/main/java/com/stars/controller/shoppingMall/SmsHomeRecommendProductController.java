package com.stars.controller.shoppingMall;

import com.stars.common.util.PageResult;
import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.SmsHomeRecommendProductMapper;
import com.stars.pojo.shoppingMall.SmsHomeRecommendProduct;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "SmsHomeRecommendProductController", description = "首页人气推荐管理")
@RequestMapping("/homeRecommendProduct")
@CrossOrigin
public class SmsHomeRecommendProductController {
    @Autowired
    private SmsHomeRecommendProductMapper smsHomeRecommendProductMapper;

    @ApiOperation("分页查询人气推荐")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData homeRecommendProductList(@RequestParam(value = "productName", required = false) String productName,
                                                 @RequestParam(value = "recommendStatus", required = false) Integer recommendStatus,
                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SmsHomeRecommendProduct> smsHomeRecommendProducts = smsHomeRecommendProductMapper.selectWay(productName, recommendStatus);
        // 分页
        PageInfo<SmsHomeRecommendProduct> pageInfo = new PageInfo<SmsHomeRecommendProduct>(smsHomeRecommendProducts);
        PageResult pageResult = PageResult.getPageResult(pageInfo);
        return ResponseDataUtil.buildSuccess(pageResult);
    }
    /*
        传参：
            [
                {
                    "productId": 26,
                    "productName": "哈哥"
                }
            ]
    */
    @ApiOperation("批量添加首页推荐")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData homeRecommendProductAdd(@RequestBody List<SmsHomeRecommendProduct> homeRecommendProductList) {
        for(SmsHomeRecommendProduct item: homeRecommendProductList) {
            item.setRecommendStatus(1);
            item.setSort(0);
            smsHomeRecommendProductMapper.insertWay(item);
        }
        return ResponseDataUtil.countJudge(homeRecommendProductList.size());
    }
    @ApiOperation("批量修改首页推荐")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData homeRecommendProductUpdate(@RequestBody SmsHomeRecommendProduct homeRecommendProductList) {
        int count = smsHomeRecommendProductMapper.updateWay(homeRecommendProductList);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("批量删除推荐")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseData homeRecommendProductDelete(@RequestParam("ids") List<Long> ids) {
        int count = smsHomeRecommendProductMapper.deleteWay(ids);
        return ResponseDataUtil.countJudge(count);
    }
}
