package com.stars.controller.shoppingMall;

import com.stars.apiParams.PmsProductAttributeAddParam;
import com.stars.common.util.PageResult;
import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.PmsProductAttributeCategoryMapper;
import com.stars.mapper.shoppingMall.PmsProductAttributeMapper;
import com.stars.pojo.shoppingMall.PmsProductAttribute;
import com.stars.pojo.shoppingMall.PmsProductAttributeCategory;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "PmsProductAttributeController", description = "商品属性管理")
@RequestMapping("/productAttribute")
@CrossOrigin
public class PmsProductAttributeController {
    @Autowired
    private PmsProductAttributeMapper pmsProductAttributeMapper;
    @Autowired
    private PmsProductAttributeCategoryMapper pmsProductAttributeCategoryMapper;
    /*
        分页查询,pageSize传9999查所有
    */
    @ApiOperation("根据分类查询属性列表或参数列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "type", value = "0表示属性，1表示参数", required = true, paramType = "query", dataType = "integer")})
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData productAttributeList(@RequestParam(value = "cid") Long cid,
                                             @RequestParam(value = "type") Integer type,
                                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PmsProductAttribute> pmsProductAttributes = pmsProductAttributeMapper.selectWay(cid, type);
        // 分页
        PageInfo<PmsProductAttribute> pageInfo = new PageInfo<PmsProductAttribute>(pmsProductAttributes);
        PageResult pageResult = PageResult.getPageResult(pageInfo);
        return ResponseDataUtil.buildSuccess(pageResult);
    }
    /*
        实际传参：
        {
            "filterType": 0,
            "handAddStatus": 0,
            "inputList": "对666的",
            "inputType": 0,
            "name": "26二哈62",
            "productAttributeCategoryId": 1,
            "relatedStatus": 0,
            "searchType": 0,
            "selectType": 0,
            "sort": 0,
            "type": 0
        }
    */
    @ApiOperation("添加商品属性信息")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData productAttributeAdd(@RequestBody PmsProductAttributeAddParam pmsProductAttributeAddParam) {
        PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
        BeanUtils.copyProperties(pmsProductAttributeAddParam, pmsProductAttribute);
        int count = pmsProductAttributeMapper.insertWay(pmsProductAttribute);
        // SQL查询商品属性分类，然后数量计算
        PmsProductAttributeCategory pmsProductAttributeCategory = pmsProductAttributeCategoryMapper.selectById(pmsProductAttribute.getProductAttributeCategoryId());
        if(pmsProductAttribute.getType()==0){
            pmsProductAttributeCategory.setAttributeCount(pmsProductAttributeCategory.getAttributeCount()+1);
        }else if(pmsProductAttribute.getType()==1){
            pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount()+1);
        }
        // SQL修改商品属性分类
        pmsProductAttributeCategoryMapper.updateWay(pmsProductAttributeCategory);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("修改商品属性信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData productAttributeUpdate(@RequestBody PmsProductAttributeAddParam pmsProductAttributeAddParam) {
        PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
        BeanUtils.copyProperties(pmsProductAttributeAddParam, pmsProductAttribute);
        int count = pmsProductAttributeMapper.updateWay(pmsProductAttribute);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("批量删除商品属性")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseData productAttributeDelete(@RequestParam("ids") List<Long> ids) {
        // SQL查询一条商品属性，得出type值-----注意顺序，先删除就查询不出来了
        Long aLong = ids.get(0);
        PmsProductAttribute pmsProductAttribute = pmsProductAttributeMapper.selectById(aLong);

        for(Long item: ids) {
            // SQL删除
            pmsProductAttributeMapper.deleteWay(item);
        }

        System.out.println("--pmsProductAttribute--"+pmsProductAttribute);
        System.out.println("--ids.get(0)--"+ids.get(0));

        // SQL查询商品属性分类，然后数量计算
        PmsProductAttributeCategory pmsProductAttributeCategory = pmsProductAttributeCategoryMapper.selectById(pmsProductAttribute.getProductAttributeCategoryId());
        int count = ids.size();
        if(pmsProductAttribute.getType()==0){
            if(pmsProductAttributeCategory.getAttributeCount()>=count){
                pmsProductAttributeCategory.setAttributeCount(pmsProductAttributeCategory.getAttributeCount()-count);
            }else{
                pmsProductAttributeCategory.setAttributeCount(0);
            }
        }else if(pmsProductAttribute.getType()==1){
            if(pmsProductAttributeCategory.getParamCount()>=count){
                pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount()-count);
            }else{
                pmsProductAttributeCategory.setParamCount(0);
            }
        }
        // SQL修改商品属性分类
        pmsProductAttributeCategoryMapper.updateWay(pmsProductAttributeCategory);
        return ResponseDataUtil.countJudge(count);
    }
}
