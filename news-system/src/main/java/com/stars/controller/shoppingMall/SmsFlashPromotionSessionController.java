package com.stars.controller.shoppingMall;

import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.SmsFlashPromotionProductRelationMapper;
import com.stars.mapper.shoppingMall.SmsFlashPromotionSessionMapper;
import com.stars.pojo.shoppingMall.SmsFlashPromotionSession;
import com.stars.pojo.shoppingMall.SmsFlashPromotionSessionDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "SmsFlashPromotionSessionController", description = "限时购场次管理")
@RequestMapping("/flashPromotionSession")
@CrossOrigin
public class SmsFlashPromotionSessionController {
    @Autowired
    private SmsFlashPromotionSessionMapper smsFlashPromotionSessionMapper;
    @Autowired
    private SmsFlashPromotionProductRelationMapper smsFlashPromotionProductRelationMapper;

    @ApiOperation("获取全部场次")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData flashPromotionSessionList() {
        List<SmsFlashPromotionSession> smsFlashPromotionSessions = smsFlashPromotionSessionMapper.selectWay(null);
        return ResponseDataUtil.buildSuccess(smsFlashPromotionSessions);
    }
    /*
        传参：
            {
                "name": "二哈",
                "startTime": "2022-06-22T16:15:28.000Z",
                "endTime": "2022-06-22T16:15:34.000Z",
                "status": 1
            }
    */
    @ApiOperation("添加场次")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData flashPromotionSessionAdd(@RequestBody SmsFlashPromotionSession smsFlashPromotionSession) {
        smsFlashPromotionSession.setCreateTime(new Date());
        int count = smsFlashPromotionSessionMapper.insertWay(smsFlashPromotionSession);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("修改场次")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData flashPromotionSessionUpdate(@RequestBody SmsFlashPromotionSession smsFlashPromotionSession) {
        int count = smsFlashPromotionSessionMapper.updateWay(smsFlashPromotionSession);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("删除场次")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseData flashPromotionSessionDelete(@PathVariable Long id) {
        int count = smsFlashPromotionSessionMapper.deleteWay(id);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("获取全部可选场次及其数量")
    @RequestMapping(value = "/status/count", method = RequestMethod.GET)
    public ResponseData flashPromotionSessionStatusCount(@RequestParam Long flashPromotionId) {
        List<SmsFlashPromotionSession> smsFlashPromotionSessions = smsFlashPromotionSessionMapper.selectWay(1);
        List<SmsFlashPromotionSessionDetail> result = new ArrayList<>();
        for (SmsFlashPromotionSession item: smsFlashPromotionSessions) {
            SmsFlashPromotionSessionDetail detail = new SmsFlashPromotionSessionDetail();
            BeanUtils.copyProperties(item, detail);
            long count = smsFlashPromotionProductRelationMapper.selectCount(flashPromotionId, item.getId());
            detail.setProductCount(count);
            result.add(detail);
        }
        return ResponseDataUtil.buildSuccess(result);
    }
}
