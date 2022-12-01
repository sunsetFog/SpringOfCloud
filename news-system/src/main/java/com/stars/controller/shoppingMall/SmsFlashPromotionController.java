package com.stars.controller.shoppingMall;

import com.stars.common.util.PageResult;
import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.SmsFlashPromotionMapper;
import com.stars.pojo.shoppingMall.SmsFlashPromotion;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "SmsFlashPromotionController", description = "限时购活动管理")
@RequestMapping("/flashPromotion")
@CrossOrigin
public class SmsFlashPromotionController {
    @Autowired
    private SmsFlashPromotionMapper smsFlashPromotionMapper;

    @ApiOperation("根据活动名称分页查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData flashPromotionList(@RequestParam(value = "keyword", required = false) String keyword,
                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SmsFlashPromotion> smsFlashPromotions = smsFlashPromotionMapper.selectWay(keyword);
        // 分页
        PageInfo<SmsFlashPromotion> pageInfo = new PageInfo<SmsFlashPromotion>(smsFlashPromotions);
        PageResult pageResult = PageResult.getPageResult(pageInfo);
        return ResponseDataUtil.buildSuccess(pageResult);
    }
    /*
        传参：
            {
                "title": "风格",
                "startDate": "2022-06-20T16:00:00.000Z",
                "endDate": "2022-06-22T16:00:00.000Z",
                "status": 1
            }
    */
    @ApiOperation("添加活动")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData flashPromotionAdd(@RequestBody SmsFlashPromotion smsFlashPromotion) {
        smsFlashPromotion.setCreateTime(new Date());
        int count = smsFlashPromotionMapper.insertWay(smsFlashPromotion);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("编辑活动")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData flashPromotionUpdate(@RequestBody SmsFlashPromotion smsFlashPromotion) {
        int count = smsFlashPromotionMapper.updateWay(smsFlashPromotion);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("删除活动")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseData flashPromotionDelete(@PathVariable Long id) {
        int count = smsFlashPromotionMapper.deleteWay(id);
        return ResponseDataUtil.countJudge(count);
    }
}
