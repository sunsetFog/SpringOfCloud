package com.stars.controller.shoppingMall;

import com.stars.common.util.PageResult;
import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.PmsProductAttributeCategoryMapper;
import com.stars.pojo.shoppingMall.PmsProductAttributeCategory;
import com.stars.pojo.shoppingMall.PmsProductAttributeCategoryItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "PmsProductAttributeCategoryController", description = "商品属性分类管理")
@RequestMapping("/PmsProductAttributeCategory")
@CrossOrigin
public class PmsProductAttributeCategoryController {
    @Autowired
    private PmsProductAttributeCategoryMapper pmsProductAttributeCategoryMapper;
    /*
        分页查询,pageSize传9999查所有
    */
    @ApiOperation("分页获取所有商品属性分类")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData PmsProductAttributeCategoryList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PmsProductAttributeCategory> pmsProductAttributeCategories = pmsProductAttributeCategoryMapper.selectWay();
        // 分页
        PageInfo<PmsProductAttributeCategory> pageInfo = new PageInfo<PmsProductAttributeCategory>(pmsProductAttributeCategories);
        PageResult pageResult = PageResult.getPageResult(pageInfo);
        return ResponseDataUtil.buildSuccess(pageResult);
    }
    @ApiOperation("获取所有商品属性分类及其下属性")
    @RequestMapping(value = "/list/withAttr", method = RequestMethod.GET)
    public ResponseData getListWithAttr() {
        List<PmsProductAttributeCategoryItem> pmsProductAttributeCategoryItems = pmsProductAttributeCategoryMapper.selectCategoryAndAttribute();
        return ResponseDataUtil.buildSuccess(pmsProductAttributeCategoryItems);
    }
    @ApiOperation("添加商品属性分类")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData PmsProductAttributeCategoryAdd(@RequestBody PmsProductAttributeCategory pmsProductAttributeCategory) {
        System.out.println("--PmsProductAttributeCategory--"+pmsProductAttributeCategory);
        int count = pmsProductAttributeCategoryMapper.insertWay(pmsProductAttributeCategory);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("修改商品属性分类")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData PmsProductAttributeCategoryUpdate(@RequestBody PmsProductAttributeCategory pmsProductAttributeCategory) {
        int count = pmsProductAttributeCategoryMapper.updateWay(pmsProductAttributeCategory);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("删除单个商品属性分类")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseData PmsProductAttributeCategoryDelete(@PathVariable Long id) {
        int count = pmsProductAttributeCategoryMapper.deleteWay(id);
        return ResponseDataUtil.countJudge(count);
    }
}
