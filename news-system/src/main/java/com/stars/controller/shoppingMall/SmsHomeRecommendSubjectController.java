package com.stars.controller.shoppingMall;

import com.stars.common.util.PageResult;
import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.SmsHomeRecommendSubjectMapper;
import com.stars.pojo.shoppingMall.SmsHomeRecommendSubject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "SmsHomeRecommendSubjectController", description = "首页专题推荐管理")
@RequestMapping("/homeRecommendSubject")
@CrossOrigin
public class SmsHomeRecommendSubjectController {
    @Autowired
    private SmsHomeRecommendSubjectMapper smsHomeRecommendSubjectMapper;

    @ApiOperation("分页查询推荐")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData homeRecommendSubjectList(@RequestParam(value = "subjectName", required = false) String subjectName,
                                                 @RequestParam(value = "recommendStatus", required = false) Integer recommendStatus,
                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SmsHomeRecommendSubject> smsHomeRecommendSubjects = smsHomeRecommendSubjectMapper.selectWay(subjectName, recommendStatus);
        // 分页
        PageInfo<SmsHomeRecommendSubject> pageInfo = new PageInfo<SmsHomeRecommendSubject>(smsHomeRecommendSubjects);
        PageResult pageResult = PageResult.getPageResult(pageInfo);
        return ResponseDataUtil.buildSuccess(pageResult);
    }
    /*
        传参：
            [
                {
                    "subjectId": 1,
                    "subjectName": "哈嘎226"
                }
            ]
    */
    @ApiOperation("批量添加首页推荐专题")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData homeRecommendSubjectAdd(@RequestBody List<SmsHomeRecommendSubject> homeRecommendSubjectList) {
        for (SmsHomeRecommendSubject item: homeRecommendSubjectList) {
            item.setRecommendStatus(1);
            item.setSort(0);
            smsHomeRecommendSubjectMapper.insertWay(item);
        }
        return ResponseDataUtil.countJudge(homeRecommendSubjectList.size());
    }
    @ApiOperation("修改添加首页推荐专题")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData homeRecommendSubjectUpdate(@RequestBody SmsHomeRecommendSubject homeRecommendSubjectList) {
        int count = smsHomeRecommendSubjectMapper.updateWay(homeRecommendSubjectList);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("批量删除推荐")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseData homeRecommendSubjectDelete(@RequestParam("ids") List<Long> ids) {
        int count = smsHomeRecommendSubjectMapper.deleteWay(ids);
        return ResponseDataUtil.countJudge(count);
    }
}
