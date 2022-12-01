package com.stars.controller.shoppingMall;

import com.stars.apiParams.PmsProductCategoryAddParam;
import com.stars.common.util.PageResult;
import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.PmsProductCategoryAttributeRelationMapper;
import com.stars.mapper.shoppingMall.PmsProductCategoryMapper;
import com.stars.pojo.shoppingMall.*;
import com.stars.pojo.shoppingMall.PmsProductCategory;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "PmsProductCategoryController", description = "商品分类管理")
@RequestMapping("/productCategory")
@CrossOrigin
public class PmsProductCategoryController {
    @Autowired
    private PmsProductCategoryMapper pmsProductCategoryMapper;
    @Autowired
    private PmsProductCategoryAttributeRelationMapper pmsProductCategoryAttributeRelationMapper;

    @ApiOperation("商品分类树形结构")
    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    public ResponseData productCategoryTree() {
        List<PmsProductCategory> menuList = pmsProductCategoryMapper.selectWay(null);

        List<PmsProductCategoryNode> result = menuList.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuNode(menu, menuList)).collect(Collectors.toList());
        return ResponseDataUtil.buildSuccess(result);
    }
    /**
     * 将PmsProductCategory转化为PmsProductCategoryNode并设置children属性
     */
    private PmsProductCategoryNode covertMenuNode(PmsProductCategory menu, List<PmsProductCategory> menuList) {
        PmsProductCategoryNode node = new PmsProductCategoryNode();
        BeanUtils.copyProperties(menu, node);
        List<PmsProductCategoryNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }
    @ApiOperation("分页查询商品分类")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData productCategoryList(@RequestParam(value = "parentId") Long parentId,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PmsProductCategory> pmsProductCategories = pmsProductCategoryMapper.selectWay(parentId);
        // 分页
        PageInfo<PmsProductCategory> pageInfo = new PageInfo<PmsProductCategory>(pmsProductCategories);
        PageResult pageResult = PageResult.getPageResult(pageInfo);
        return ResponseDataUtil.buildSuccess(pageResult);
    }
    /*
        数据校验
        传参：
            {
                "description": "22",
                "icon": "",
                "keywords": "11",
                "name": "二哈",
                "navStatus": 0,
                "parentId": 0,
                "productUnit": "个",
                "showStatus": 0,
                "sort": 0,
                "productAttributeIdList": [
                    33
                ]
            }
    */
    @ApiOperation("添加商品分类")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData productCategoryAdd(@Validated @RequestBody PmsProductCategoryAddParam pmsProductCategoryAddParam) {
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        pmsProductCategory.setProductCount(0);
        // 类复制属性值
        BeanUtils.copyProperties(pmsProductCategoryAddParam, pmsProductCategory);
        // 设置level
        setCategoryLevel(pmsProductCategory);
        // SQL插入
        int count = pmsProductCategoryMapper.insertWay(pmsProductCategory);
        // SQL插入筛选属性
        List<Long> productAttributeIdList = pmsProductCategoryAddParam.getProductAttributeIdList();
        if(!CollectionUtils.isEmpty(productAttributeIdList)){// 判断集合是否为空
            insertRelationList(pmsProductCategory.getId(), productAttributeIdList);
        }
        return ResponseDataUtil.countJudge(count);
    }
    /**
     * 根据分类的parentId设置分类的level
     */
    private void setCategoryLevel(PmsProductCategory productCategory) {
        // 没有父分类时为一级分类
        productCategory.setLevel(0);
        if (productCategory.getParentId() == 0) return;
        // 有父分类时,SQL查询父类的level设置
        PmsProductCategory pmsProductCategory = pmsProductCategoryMapper.selectIdWay(productCategory.getParentId());
        productCategory.setLevel(pmsProductCategory.getLevel() + 1);
        System.out.println("---level---"+productCategory.getLevel());
    }
    /**
     * 批量插入商品分类与筛选属性关系表
     * @param productCategoryId 商品分类id
     * @param productAttributeIdList 相关商品筛选属性id集合
     */
    private void insertRelationList(Long productCategoryId, List<Long> productAttributeIdList) {
        List<PmsProductCategoryAttributeRelation> relationList = new ArrayList<>();
        for (Long productAttrId : productAttributeIdList) {
            PmsProductCategoryAttributeRelation relation = new PmsProductCategoryAttributeRelation();
            relation.setProductAttributeId(productAttrId);
            relation.setProductCategoryId(productCategoryId);
            relationList.add(relation);
        }
        pmsProductCategoryAttributeRelationMapper.insertList(relationList);
    }
    @ApiOperation("修改商品分类")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData productCategoryUpdate(@Validated @RequestBody PmsProductCategoryAddParam pmsProductCategoryAddParam) {
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        // 类复制属性值
        BeanUtils.copyProperties(pmsProductCategoryAddParam, pmsProductCategory);
        // 设置level
        setCategoryLevel(pmsProductCategory);
        System.out.println("--pmsProductCategory--"+pmsProductCategory);
        // SQL修改
        int count = pmsProductCategoryMapper.updateWay(pmsProductCategory);
        // SQL删除、插入分类和属性关系表
        List<Long> productAttributeIdList = pmsProductCategoryAddParam.getProductAttributeIdList();
        if(!CollectionUtils.isEmpty(productAttributeIdList)){// 判断集合是否为空
            Long productCategoryId = pmsProductCategory.getId();
            pmsProductCategoryAttributeRelationMapper.deleteWay(productCategoryId);
            insertRelationList(productCategoryId, productAttributeIdList);
        }
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("删除商品分类")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseData productCategoryDelete(@PathVariable Long id) {
        // SQL删除
        int count = pmsProductCategoryMapper.deleteWay(id);
        // SQL删关系表
        pmsProductCategoryAttributeRelationMapper.deleteWay(id);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("修改导航栏和是否显示")
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public ResponseData productCategoryStatus(@RequestParam Long id, @RequestParam Integer navStatus, @RequestParam Integer showStatus) {
        int count = pmsProductCategoryMapper.statusWay(id, navStatus, showStatus);
        return ResponseDataUtil.countJudge(count);
    }
}
