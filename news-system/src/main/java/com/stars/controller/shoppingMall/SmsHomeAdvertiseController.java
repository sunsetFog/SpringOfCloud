package com.stars.controller.shoppingMall;

import com.stars.common.util.PageResult;
import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.SmsHomeAdvertiseMapper;
import com.stars.pojo.shoppingMall.SmsHomeAdvertise;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "SmsHomeAdvertiseController", description = "首页轮播广告管理")
@RequestMapping("/homeAdvertise")
@CrossOrigin
public class SmsHomeAdvertiseController {
    @Autowired
    private SmsHomeAdvertiseMapper smsHomeAdvertiseMapper;

    @ApiOperation("分页查询广告")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData homeAdvertiseList(@RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "type", required = false) Integer type,
                                          @RequestParam(value = "endTime") String endTime,
                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        System.out.println("--hhh.--"+name+"--2--"+type+"--3--"+endTime);
//        Date start = null;
//        Date end = null;
//        if (!StringUtils.isEmpty(endTime)) {
//            System.out.println("---------不等于空---------");
//            String startStr = endTime + " 00:00:00";
//            String endStr = endTime + " 23:59:59";
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            try {
//                start = sdf.parse(startStr);
//                end = sdf.parse(endStr);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("--yyy.--"+name+"--2--"+type+"--3--"+end);
        List<SmsHomeAdvertise> smsHomeAdvertises = smsHomeAdvertiseMapper.selectWay(name, type, endTime);
        // 分页
        PageInfo<SmsHomeAdvertise> pageInfo = new PageInfo<SmsHomeAdvertise>(smsHomeAdvertises);
        PageResult pageResult = PageResult.getPageResult(pageInfo);
        return ResponseDataUtil.buildSuccess(pageResult);
    }
    /*
        传参：
            {
                "name": "二哈",
                "type": 1,
                "pic": null,
                "startTime": "2022-06-23T16:00:00.000Z",
                "endTime": "2022-06-24T16:00:00.000Z",
                "status": 1,
                "url": "link",
                "note": "应用",
                "sort": "10"
            }
    */
    @ApiOperation("添加广告")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData homeAdvertiseAdd(@RequestBody SmsHomeAdvertise smsHomeAdvertise) {
        smsHomeAdvertise.setClickCount(0);
        smsHomeAdvertise.setOrderCount(0);
        int count = smsHomeAdvertiseMapper.insertWay(smsHomeAdvertise);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("修改广告")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData homeAdvertiseUpdate(@RequestBody SmsHomeAdvertise smsHomeAdvertise) {
        int count = smsHomeAdvertiseMapper.updateWay(smsHomeAdvertise);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("删除广告")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseData homeAdvertiseDelete(@RequestParam("ids") List<Long> ids) {
        int count = smsHomeAdvertiseMapper.deleteWay(ids);
        return ResponseDataUtil.countJudge(count);
    }
}
