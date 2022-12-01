package com.stars.controller.shoppingMall;

import com.stars.apiParams.PmsProductAddParam;
import com.stars.apiParams.PmsProductListParam;
import com.stars.common.util.PageResult;
import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.*;
import com.stars.pojo.shoppingMall.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@Api(tags = "PmsProductController", description = "商品管理")
@RequestMapping("/product")
@CrossOrigin
public class PmsProductController {
    @Autowired
    private PmsProductMapper pmsProductMapper;
    @Autowired
    private PmsProductLadderMapper pmsProductLadderMapper;
    @Autowired
    private PmsProductFullReductionMapper pmsProductFullReductionMapper;
    @Autowired
    private PmsMemberPriceMapper pmsMemberPriceMapper;
    @Autowired
    private PmsSkuStockMapper pmsSkuStockMapper;
    @Autowired
    private PmsProductAttributeValueMapper pmsProductAttributeValueMapper;
    @Autowired
    private CmsSubjectProductRelationMapper cmsSubjectProductRelationMapper;
    @Autowired
    private CmsPrefrenceAreaProductRelationMapper cmsPrefrenceAreaProductRelationMapper;
    /*
        分页查询,pageSize传9999查所有
        name字段模糊搜索
        传参：
            {
                "keyword": "华为 HUAWEI P2",
                "productSn": "6946605",
                "productCategoryId": 19,
                "brandId": 3,
                "publishStatus": 1,
                "verifyStatus": 0,
                "pageNum": 1,
                "pageSize": 5
            }
    */
    @ApiOperation("查询商品")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseData productList(@RequestBody PmsProductListParam productQueryParam){
        PageHelper.startPage(productQueryParam.getPageNum(), productQueryParam.getPageSize());
        System.out.println("--productQueryParam--"+productQueryParam);
        List<PmsProductAddParam> pmsProducts = pmsProductMapper.selectWay(productQueryParam);
        // 分页
        PageInfo<PmsProductAddParam> pageInfo = new PageInfo<PmsProductAddParam>(pmsProducts);
        PageResult pageResult = PageResult.getPageResult(pageInfo);
        return ResponseDataUtil.buildSuccess(pageResult);
    }
    @ApiOperation("创建商品")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData productAdd(@RequestBody PmsProductAddParam pmsProductAddParam) {
        // SQL插入
        int count = pmsProductMapper.insertWay(pmsProductAddParam);
        // 关系表的SQL批量插入
        // 根据促销类型设置价格：会员价格、阶梯价格、满减价格
        Long productId = pmsProductAddParam.getId();
        System.out.println("---productId--add---"+productId);
        // 阶梯价格
        relateAndInsertList(pmsProductLadderMapper, pmsProductAddParam.getProductLadderList(), productId);
        //满减价格
        relateAndInsertList(pmsProductFullReductionMapper, pmsProductAddParam.getProductFullReductionList(), productId);
        //会员价格
        relateAndInsertList(pmsMemberPriceMapper, pmsProductAddParam.getMemberPriceList(), productId);
        // 添加sku库存信息
        handleSkuStockCode(pmsProductAddParam.getSkuStockList(),productId);// 处理sku的编码
        relateAndInsertList(pmsSkuStockMapper, pmsProductAddParam.getSkuStockList(), productId);
        // 添加商品参数,添加自定义商品规格
        relateAndInsertList(pmsProductAttributeValueMapper, pmsProductAddParam.getProductAttributeValueList(), productId);
        // 关联专题
        relateAndInsertList(cmsSubjectProductRelationMapper, pmsProductAddParam.getSubjectProductRelationList(), productId);
        // 关联优选
        relateAndInsertList(cmsPrefrenceAreaProductRelationMapper, pmsProductAddParam.getPrefrenceAreaProductRelationList(), productId);

        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("更新商品")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData productUpdate(@RequestBody PmsProductAddParam pmsProductAddParam) {
        // SQL修改
        int count = pmsProductMapper.updateWay(pmsProductAddParam);
        // 关系表的SQL删除、批量插入
        // 根据促销类型设置价格：会员价格、阶梯价格、满减价格
        Long productId = pmsProductAddParam.getId();
        System.out.println("---productId--update---"+productId);
        // 阶梯价格
        pmsProductLadderMapper.deleteWay(productId);
        relateAndInsertList(pmsProductLadderMapper, pmsProductAddParam.getProductLadderList(), productId);
        //满减价格
        pmsProductFullReductionMapper.deleteWay(productId);
        relateAndInsertList(pmsProductFullReductionMapper, pmsProductAddParam.getProductFullReductionList(), productId);
        //会员价格
        pmsMemberPriceMapper.deleteWay(productId);
        relateAndInsertList(pmsMemberPriceMapper, pmsProductAddParam.getMemberPriceList(), productId);
        // 添加sku库存信息
        handleSkuStockCode(pmsProductAddParam.getSkuStockList(),productId);// 处理sku的编码
        pmsSkuStockMapper.deleteWay(productId);
        relateAndInsertList(pmsSkuStockMapper, pmsProductAddParam.getSkuStockList(), productId);
        // 添加商品参数,添加自定义商品规格
        pmsProductAttributeValueMapper.deleteWay(productId);
        relateAndInsertList(pmsProductAttributeValueMapper, pmsProductAddParam.getProductAttributeValueList(), productId);
        // 关联专题
        cmsSubjectProductRelationMapper.deleteWay(productId);
        relateAndInsertList(cmsSubjectProductRelationMapper, pmsProductAddParam.getSubjectProductRelationList(), productId);
        // 关联优选
        cmsPrefrenceAreaProductRelationMapper.deleteWay(productId);
        relateAndInsertList(cmsPrefrenceAreaProductRelationMapper, pmsProductAddParam.getPrefrenceAreaProductRelationList(), productId);

        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("批量修改删除状态")
    @RequestMapping(value = "/deleteStatus", method = RequestMethod.GET)
    public ResponseData productDelete(@RequestParam("ids") List<Long> ids,
                                      @RequestParam(value = "deleteStatus", defaultValue = "1") Integer deleteStatus) {
        for (Long item: ids) {
            pmsProductMapper.update_deleteStatus(item, deleteStatus);
        }
        return ResponseDataUtil.buildSuccess(ids.size());
    }
    /**
     * 建立和插入关系表操作
     *
     * @param dao       可以操作的dao
     * @param dataList  要插入的数据
     * @param productId 建立关系的id
     */
    private void relateAndInsertList(Object dao, List dataList, Long productId) {
        try {
            // 判断dataList空数组就结束方法
            if (CollectionUtils.isEmpty(dataList)) return;
            for (Object item : dataList) {
                // 获取方法： item.获取类名.获取方法(方法名, 方法参数类型类)
                Method setId = item.getClass().getMethod("setId", Long.class);
                // 调用方法： invoke(item, 方法参数值)
                setId.invoke(item, (Long) null);
                Method setProductId = item.getClass().getMethod("setProductId", Long.class);
                setProductId.invoke(item, productId);
            }
            Method insertList = dao.getClass().getMethod("insertList", List.class);
            insertList.invoke(dao, dataList);
        } catch (Exception e) {
            // 异常日志
            log.warn("创建产品出错:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
    private void handleSkuStockCode(List<PmsSkuStock> skuStockList, Long productId) {
        if(CollectionUtils.isEmpty(skuStockList))return;
        for(int i=0;i<skuStockList.size();i++){
            PmsSkuStock skuStock = skuStockList.get(i);
            if(StringUtils.isEmpty(skuStock.getSkuCode())){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                StringBuilder sb = new StringBuilder();
                //日期
                sb.append(sdf.format(new Date()));
                //四位商品id
                sb.append(String.format("%04d", productId));
                //三位索引id
                sb.append(String.format("%03d", i+1));
                skuStock.setSkuCode(sb.toString());
            }
        }
    }
}
