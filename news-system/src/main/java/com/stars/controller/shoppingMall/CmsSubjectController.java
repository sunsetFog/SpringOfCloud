package com.stars.controller.shoppingMall;

import com.stars.common.util.PageResult;
import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.CmsSubjectMapper;
import com.stars.pojo.shoppingMall.CmsSubject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "CmsSubjectController", description = "商品专题管理")
@RequestMapping("/subject")
@CrossOrigin
public class CmsSubjectController {
    @Autowired
    private CmsSubjectMapper cmsSubjectMapper;

    @ApiOperation("获取全部商品专题")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData subjectList(@RequestParam(value = "keyword", required = false) String keyword,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CmsSubject> cmsSubjects = cmsSubjectMapper.selectWay(keyword);
        // 分页
        PageInfo<CmsSubject> pageInfo = new PageInfo<CmsSubject>(cmsSubjects);
        PageResult pageResult = PageResult.getPageResult(pageInfo);
        return ResponseDataUtil.buildSuccess(pageResult);
    }
}
