package com.stars.controller.shoppingMall;

import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.PmsProductCategoryAttributeRelationMapper;
import com.stars.pojo.shoppingMall.PmsProductCategoryAttributeRelation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "PmsProductCategoryAttributeRelationController", description = "商品分类 和 商品分类属性 关系表")
@RequestMapping("/product_category_attribute")
@CrossOrigin
public class PmsProductCategoryAttributeRelationController {
    @Autowired
    private PmsProductCategoryAttributeRelationMapper pmsProductCategoryAttributeRelationMapper;

    @ApiOperation("商品分类id 查 商品分类属性")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData product_category_attribute_list(@RequestParam Long productCategoryId) {
        List<PmsProductCategoryAttributeRelation> pmsProductCategoryAttributeRelations = pmsProductCategoryAttributeRelationMapper.selectWay(productCategoryId);
        return ResponseDataUtil.buildSuccess(pmsProductCategoryAttributeRelations);
    }
}
