package com.stars.controller.shoppingMall;

import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.PmsSkuStockMapper;
import com.stars.pojo.shoppingMall.PmsSkuStock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "PmsSkuStockController", description = "sku商品库存管理")
@RequestMapping("/sku")
@CrossOrigin
public class PmsSkuStockController {
    @Autowired
    private PmsSkuStockMapper pmsSkuStockMapper;

    @ApiOperation("根据商品ID及sku编码模糊搜索sku库存")
    @RequestMapping(value = "/list/{productId}", method = RequestMethod.GET)
    public ResponseData skuList(@PathVariable Long productId, @RequestParam(value = "keyword",required = false) String keyword) {
        List<PmsSkuStock> pmsSkuStocks = pmsSkuStockMapper.selectWay(productId, keyword);
        return ResponseDataUtil.buildSuccess(pmsSkuStocks);
    }

    @ApiOperation("批量更新sku库存信息")
    @RequestMapping(value ="/update/{productId}",method = RequestMethod.POST)
    public ResponseData skuUpdate(@PathVariable Long productId, @RequestBody List<PmsSkuStock> skuStockList) {
        int count = pmsSkuStockMapper.replaceList(skuStockList);
        return ResponseDataUtil.countJudge(count);
    }
}
