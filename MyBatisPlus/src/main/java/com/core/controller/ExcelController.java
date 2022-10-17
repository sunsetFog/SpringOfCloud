package com.core.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.core.mapper.GoodsMapper;
import com.core.pojo.Goods;
import lombok.extern.slf4j.Slf4j;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@CrossOrigin
@Slf4j
public class ExcelController {
    @Autowired
    private GoodsMapper goodsMapper;
    @Value("${jeecg.path.upload}")
    private String upLoadPath;
    @PostMapping("/exportXls")
    public ModelAndView ExcelGoods(Goods goods) {
        // Step.1 组装查询条件
//        QueryWrapper<Goods> queryWrapper = QueryGenerator.initQueryWrapper(goods, request.getParameterMap());
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        List<Goods> pageList = goodsMapper.selectList(queryWrapper);
        System.out.println("--pageList--"+pageList);

        //Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());

        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "goods列表");
        mv.addObject(NormalExcelConstants.CLASS, Goods.class);

        ExportParams exportParams = new ExportParams("goods列表数据", "导出人:rafael", "导出信息");
        exportParams.setImageBasePath(upLoadPath);
        mv.addObject(NormalExcelConstants.PARAMS, exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }
}
