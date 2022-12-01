package com.stars.controller.shoppingMall;

import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.UmsResourceCategoryMapper;
import com.stars.pojo.shoppingMall.UmsResourceCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "UmsResourceCategoryController", description = "后台资源分类管理")
@RequestMapping("/resourceCategory")
@CrossOrigin
public class UmsResourceCategoryController {
    @Autowired
    private UmsResourceCategoryMapper umsResourceCategoryMapper;

    @ApiOperation("查询所有后台资源分类")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData resourceCategoryList() {
        List<UmsResourceCategory> umsResourceCategories = umsResourceCategoryMapper.selectWay();
        return ResponseDataUtil.buildSuccess(umsResourceCategories);
    }
    /*
        实际传参：
            {
                "name": "二哈",
                "sort": 0
            }
    */
    @ApiOperation("添加后台资源分类")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData resourceCategoryAdd(@RequestBody UmsResourceCategory umsResourceCategory) {
        umsResourceCategory.setCreateTime(new Date());
        int count = umsResourceCategoryMapper.insertWay(umsResourceCategory);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("修改后台资源分类")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData resourceCategoryUpdate(@RequestBody UmsResourceCategory umsResourceCategory) {
        int count = umsResourceCategoryMapper.updateWay(umsResourceCategory);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("根据ID删除后台资源")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseData resourceCategoryDelete(@PathVariable Long id) {
        int count = umsResourceCategoryMapper.deleteWay(id);
        return ResponseDataUtil.countJudge(count);
    }
}
