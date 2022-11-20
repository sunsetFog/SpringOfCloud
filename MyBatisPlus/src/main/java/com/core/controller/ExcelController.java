package com.core.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.core.common.util.ExcelUtils;
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

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * study: jeecgboot导出
 * SpringBoot中使用JeecgBoot的Autopoi导出Excel的方法步骤
 * https://www.php1.cn/detail/SpringBoot_Zhong_fbe5d64d.html
 * https://blog.csdn.net/qq_40065776/article/details/107824221  也有数据字典
 * 遇到问题：JeecgBoot的Autopoi导出Excel内容乱码
 */
@RestController
@CrossOrigin
@Slf4j
public class ExcelController {
    @Autowired
    private GoodsMapper goodsMapper;

    @GetMapping("/exportXls")
    public ModelAndView ExcelGoods(@RequestParam String filename, HttpServletResponse response) throws UnsupportedEncodingException {


        //设置响应的文件格式为excel  url编码格式
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        /**
         * axios无法获取响应头headers的Content-Disposition
         * https://blog.csdn.net/qq_41996454/article/details/126018683
         * 前端res.headers['content-disposition'] 获取不到 Content-Disposition   需要列出来
         *
         */
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        // header不能显示中文，需要编码
        String encode = URLEncoder.encode(filename.concat(".xls"), "UTF-8");
        System.out.println("-编码-"+encode);
        // Content-Disposition中文乱码   未解决  放弃这个字段了
        response.setHeader("Content-Disposition", "attachment;filename="+encode);
//        response.setCharacterEncoding("utf-8");
        // Step.1 组装查询条件
//        QueryWrapper<Goods> queryWrapper = QueryGenerator.initQueryWrapper(goods, request.getParameterMap());
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        List<Goods> pageList = goodsMapper.selectList(queryWrapper);
        System.out.println("--pageList--"+pageList);

        return ExcelUtils.export(filename, Goods.class, pageList);
    }
}
