package com.stars.controller.shoppingMall;

import com.stars.common.util.PageResult;
import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.SmsFlashPromotionProductRelationMapper;
import com.stars.pojo.shoppingMall.SmsFlashPromotionProduct;
import com.stars.pojo.shoppingMall.SmsFlashPromotionProductRelation;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "SmsFlashPromotionProductRelationController", description = "限时购和商品关系管理")
@RequestMapping("/flashPromotionProductRelation")
@CrossOrigin
public class SmsFlashPromotionProductRelationController {
    @Autowired
    private SmsFlashPromotionProductRelationMapper smsFlashPromotionProductRelationMapper;

    @ApiOperation("分页查询不同场次关联及商品信息")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData flashPromotionProductRelationList(@RequestParam(value = "flashPromotionId") Long flashPromotionId,
                                                          @RequestParam(value = "flashPromotionSessionId") Long flashPromotionSessionId,
                                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SmsFlashPromotionProduct> smsFlashPromotionProducts = smsFlashPromotionProductRelationMapper.selectWay(flashPromotionId, flashPromotionSessionId);
        // 分页
        PageInfo<SmsFlashPromotionProduct> pageInfo = new PageInfo<SmsFlashPromotionProduct>(smsFlashPromotionProducts);
        PageResult pageResult = PageResult.getPageResult(pageInfo);
        return ResponseDataUtil.buildSuccess(pageResult);
    }
    /*
        传参：
            [
                {
                    "productId": 26,
                    "flashPromotionId": 2,
                    "flashPromotionSessionId": 1
                }
            ]
    */
    @ApiOperation("批量选择商品添加关联")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData flashPromotionProductRelationAdd(@RequestBody List<SmsFlashPromotionProductRelation> relationList) {
        for (SmsFlashPromotionProductRelation item: relationList) {
            smsFlashPromotionProductRelationMapper.insertWay(item);
        }
        return ResponseDataUtil.buildSuccess(relationList.size());
    }
    /*
        传参：
            {
                "flashPromotionCount": 10,
                "flashPromotionId": 2,
                "flashPromotionLimit": 1,
                "flashPromotionPrice": 4998,
                "flashPromotionSessionId": 1,
                "id": 4,
                "productId": 29,
                "sort": 100
            }
    */
    @ApiOperation("修改关联信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData flashPromotionProductRelationUpdate(@RequestBody SmsFlashPromotionProductRelation smsFlashPromotionProductRelation) {
        int count = smsFlashPromotionProductRelationMapper.updateWay(smsFlashPromotionProductRelation);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("删除关联")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseData flashPromotionProductRelationDelete(@PathVariable Long id) {
        int count = smsFlashPromotionProductRelationMapper.deleteWay(id);
        return ResponseDataUtil.countJudge(count);
    }
}
