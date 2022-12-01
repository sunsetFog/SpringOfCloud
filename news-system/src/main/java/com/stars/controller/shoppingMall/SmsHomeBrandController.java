package com.stars.controller.shoppingMall;

import com.stars.common.util.PageResult;
import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.SmsHomeBrandMapper;
import com.stars.pojo.shoppingMall.SmsHomeBrand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "SmsHomeBrandController", description = "品牌推荐管理")
@RequestMapping("/homeBrand")
@CrossOrigin
public class SmsHomeBrandController {
    @Autowired
    private SmsHomeBrandMapper smsHomeBrandMapper;

    @ApiOperation("分页查询推荐品牌")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData homeBrandList(@RequestParam(value = "brandName", required = false) String brandName,
                                      @RequestParam(value = "recommendStatus", required = false) Integer recommendStatus,
                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SmsHomeBrand> smsHomeBrands = smsHomeBrandMapper.selectWay(brandName, recommendStatus);
        // 分页
        PageInfo<SmsHomeBrand> pageInfo = new PageInfo<SmsHomeBrand>(smsHomeBrands);
        PageResult pageResult = PageResult.getPageResult(pageInfo);
        return ResponseDataUtil.buildSuccess(pageResult);
    }
    /*
        传参:
            [
                {
                    "brandId": 6,
                    "brandName": "哈哥"
                }
            ]
    */
    @ApiOperation("添加首页推荐品牌")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData homeBrandAdd(@RequestBody List<SmsHomeBrand> homeBrandList) {
        for(SmsHomeBrand item: homeBrandList) {
            item.setRecommendStatus(1);
            item.setSort(0);
            smsHomeBrandMapper.insertWay(item);
        }
        return ResponseDataUtil.countJudge(homeBrandList.size());
    }
    @ApiOperation("修改首页推荐品牌")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData homeBrandUpdate(@RequestBody SmsHomeBrand homeBrandList) {
        int count = smsHomeBrandMapper.updateWay(homeBrandList);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("批量删除推荐品牌")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseData homeBrandDelete(@RequestParam("ids") List<Long> ids) {
        int count = smsHomeBrandMapper.deleteWay(ids);
        return ResponseDataUtil.countJudge(count);
    }
}
