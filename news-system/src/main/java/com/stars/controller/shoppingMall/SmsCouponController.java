package com.stars.controller.shoppingMall;

import com.stars.apiParams.SmsCouponAddParam;
import com.stars.common.util.PageResult;
import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.SmsCouponMapper;
import com.stars.mapper.shoppingMall.SmsCouponProductCategoryRelationMapper;
import com.stars.mapper.shoppingMall.SmsCouponProductRelationMapper;
import com.stars.pojo.shoppingMall.SmsCouponProductCategoryRelation;
import com.stars.pojo.shoppingMall.SmsCouponProductRelation;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "SmsCouponController", description = "优惠券管理")
@RequestMapping("/coupon")
@CrossOrigin
public class SmsCouponController {
    @Autowired
    private SmsCouponMapper smsCouponMapper;
    @Autowired
    private SmsCouponProductRelationMapper smsCouponProductRelationMapper;
    @Autowired
    private SmsCouponProductCategoryRelationMapper smsCouponProductCategoryRelationMapper;

    @ApiOperation("根据优惠券名称和类型分页获取优惠券列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData couponList(@RequestParam(value = "name",required = false) String name,
                                   @RequestParam(value = "type",required = false) Integer type,
                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SmsCouponAddParam> smsCoupons = smsCouponMapper.selectWay(name, type);
        // 分页
        PageInfo<SmsCouponAddParam> pageInfo = new PageInfo<SmsCouponAddParam>(smsCoupons);
        PageResult pageResult = PageResult.getPageResult(pageInfo);
        return ResponseDataUtil.buildSuccess(pageResult);
    }
    @ApiOperation("根据id,查询一行")
    @RequestMapping(value = "/row", method = RequestMethod.GET)
    public ResponseData couponRow(@RequestParam Long id) {
        SmsCouponAddParam smsCoupon = smsCouponMapper.selectRow(id);
        return ResponseDataUtil.buildSuccess(smsCoupon);
    }
    @ApiOperation("添加优惠券")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData couponAdd(@RequestBody SmsCouponAddParam smsCouponAddParam) {
        smsCouponAddParam.setCount(smsCouponAddParam.getPublishCount());
        smsCouponAddParam.setUseCount(0);
        smsCouponAddParam.setReceiveCount(0);
        //插入优惠券表   smsCouponAddParam自动有了id值
        int count = smsCouponMapper.insertWay(smsCouponAddParam);
        //插入优惠券和商品关系表
        if(smsCouponAddParam.getUseType().equals(2)){
            for(SmsCouponProductRelation item: smsCouponAddParam.getProductRelationList()){
                item.setCouponId(smsCouponAddParam.getId());
            }
            smsCouponProductRelationMapper.insertList(smsCouponAddParam.getProductRelationList());
        }
        //插入优惠券和商品分类关系表
        if(smsCouponAddParam.getUseType().equals(1)){
            for (SmsCouponProductCategoryRelation item: smsCouponAddParam.getProductCategoryRelationList()) {
                item.setCouponId(smsCouponAddParam.getId());
            }
            smsCouponProductCategoryRelationMapper.insertList(smsCouponAddParam.getProductCategoryRelationList());
        }
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("修改优惠券")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData couponUpdate(@RequestBody SmsCouponAddParam smsCouponAddParam) {
        int count = smsCouponMapper.updateWay(smsCouponAddParam);
        //插入和删除优惠券和商品关系表
        if(smsCouponAddParam.getUseType().equals(2)){
            for(SmsCouponProductRelation item: smsCouponAddParam.getProductRelationList()){
                item.setCouponId(smsCouponAddParam.getId());
            }
            smsCouponProductRelationMapper.deleteWay(smsCouponAddParam.getId());
            smsCouponProductRelationMapper.insertList(smsCouponAddParam.getProductRelationList());
        }
        //插入和删除优惠券和商品分类关系表
        if(smsCouponAddParam.getUseType().equals(1)){
            for (SmsCouponProductCategoryRelation item: smsCouponAddParam.getProductCategoryRelationList()) {
                item.setCouponId(smsCouponAddParam.getId());
            }
            smsCouponProductCategoryRelationMapper.deleteWay(smsCouponAddParam.getId());
            smsCouponProductCategoryRelationMapper.insertList(smsCouponAddParam.getProductCategoryRelationList());
        }
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("删除优惠券")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseData couponDelete(@PathVariable Long id) {
        // 删除优惠券
        int count = smsCouponMapper.deleteWay(id);
        // 删除优惠券和商品关系表
        smsCouponProductRelationMapper.deleteWay(id);
        // 删除优惠券和商品分类关系表
        smsCouponProductCategoryRelationMapper.deleteWay(id);
        return ResponseDataUtil.countJudge(count);
    }
}
