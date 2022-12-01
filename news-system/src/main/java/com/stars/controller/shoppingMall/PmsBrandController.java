package com.stars.controller.shoppingMall;

import com.stars.apiParams.PmsBrandAddParam;
import com.stars.apiParams.PmsProductAddParam;
import com.stars.common.util.PageResult;
import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.PmsBrandMapper;
import com.stars.mapper.shoppingMall.PmsProductMapper;
import com.stars.pojo.shoppingMall.PmsBrand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "PmsBrandController", description = "商品品牌管理")
@RequestMapping("/brand")
@CrossOrigin
public class PmsBrandController {
    @Autowired
    private PmsBrandMapper pmsBrandMapper;
    @Autowired
    private PmsProductMapper pmsProductMapper;

    /*
        分页查询,pageSize传9999查所有
        name字段模糊搜索
    */
    @ApiOperation(value = "根据品牌名称分页获取品牌列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData brandList(@RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PmsBrand> pmsBrands = pmsBrandMapper.selectWay(keyword);
        // 分页
        PageInfo<PmsBrand> pageInfo = new PageInfo<PmsBrand>(pmsBrands);
        PageResult pageResult = PageResult.getPageResult(pageInfo);
        return ResponseDataUtil.buildSuccess(pageResult);
    }
    /*
        传参：
        {
            "bigPic": "/images/20180518/5afd7778Nf7800b75.jpg",
            "brandStory": "哈哥的故事",
            "factoryStatus": 1,
            "firstLetter": "M",
            "logo": "/images/20180518/5a912944N474afb7a.png",
            "name": "二哈62",
            "productCommentCount": 100,
            "productCount": 100,
            "showStatus": 1,
            "sort": 500
        }
    */
    @ApiOperation(value = "添加品牌")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData brandAdd(@RequestBody PmsBrandAddParam pmsBrandAddParam) {
        PmsBrand pmsBrand = new PmsBrand();
        BeanUtils.copyProperties(pmsBrandAddParam, pmsBrand);
        //如果创建时首字母为空，取名称的第一个为首字母
        if (StringUtils.isEmpty(pmsBrand.getFirstLetter())) {
            pmsBrand.setFirstLetter(pmsBrand.getName().substring(0, 1));
        }
        int count = pmsBrandMapper.insertWay(pmsBrand);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation(value = "更新品牌")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData brandUpdate(@RequestBody PmsBrandAddParam pmsBrandAddParam) {
        PmsBrand pmsBrand = new PmsBrand();
        BeanUtils.copyProperties(pmsBrandAddParam, pmsBrand);
        //如果创建时首字母为空，取名称的第一个为首字母
        if (StringUtils.isEmpty(pmsBrand.getFirstLetter())) {
            pmsBrand.setFirstLetter(pmsBrand.getName().substring(0, 1));
        }
        int count = pmsBrandMapper.updateWay(pmsBrand);
        //更新品牌时要更新商品中的品牌名称
        PmsProductAddParam pmsProductAddParam = new PmsProductAddParam();
        pmsProductAddParam.setId(pmsBrand.getId());
        pmsProductAddParam.setBrandName(pmsBrand.getName());
        pmsProductMapper.updateWay(pmsProductAddParam);
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation(value = "删除品牌")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseData brandDelete(@PathVariable Long id) {
        int count = pmsBrandMapper.deleteWay(id);
        return ResponseDataUtil.countJudge(count);
    }
}
