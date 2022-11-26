package com.core.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.core.common.util.ExcelUtils;
import com.core.mapper.GoodsMapper;
import com.core.pojo.Goods;
import com.upload.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

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
    public synchronized ModelAndView ExcelGoods(@RequestParam String filename, HttpServletResponse response) throws UnsupportedEncodingException {
        System.out.println("--filename--"+filename);
        /**
         * axios无法获取响应头headers的Content-Disposition
         * https://blog.csdn.net/qq_41996454/article/details/126018683
         * 前端res.headers['content-disposition'] 获取不到 Content-Disposition   需要列出来
         *
         */
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setCharacterEncoding("UTF-8");
        // header不能显示中文，需要编码
        String encode = URLEncoder.encode(filename.concat(".xls"), "utf-8");
        System.out.println("-编码-"+encode);
        // Content-Disposition中文乱码   未解决  放弃这个字段了
        response.setHeader("Content-Disposition", "attachment;filename="+encode);
        //设置响应的文件格式为excel  url编码格式
        response.setContentType("application/vnd.ms-excel;charset=utf-8");


        // Step.1 组装查询条件
//        QueryWrapper<Goods> queryWrapper = QueryGenerator.initQueryWrapper(goods, request.getParameterMap());
//        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        List<Goods> pageList = goodsMapper.selectList(null);
        System.out.println("--pageList--"+pageList);

        return ExcelUtils.export(filename, Goods.class, pageList);
    }
    /*
        @RequestParam("file") MultipartFile file  获取上传文件对象  这只能一个个文件上传
        多文件excel导入
    */
    @RequestMapping("/importExcel")
    public Result<?> importExcel(HttpServletRequest request) throws IOException {
        // 强行转换MultipartHttpServletRequest对象
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 转map对象
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        // 转set数组，每一项就是一个文件
        Set<Map.Entry<String, MultipartFile>> entries = fileMap.entrySet();

        // 错误信息
        List<String> errorMessage = new ArrayList<>();
        // 导入成功行数、导入失败行数
        int successLines = 0, errorLines = 0;
        for (Map.Entry<String, MultipartFile> item : entries) {
            MultipartFile file = item.getValue();// 获取上传文件对象

            ImportParams params = new ImportParams();
            //表格标题所在行，计数从0开始
            params.setTitleRows(1);
            //head头部所在行，计数从0开始
            params.setHeadRows(1);
            //表格sheet数量
            params.setSheetNum(1);
            //
            params.setNeedSave(true);

            // 定义文件输入流
            InputStream inputStream = null;
            try {
                inputStream = file.getInputStream();
                List<Goods> goodsList = ExcelImportUtil.importExcel(inputStream, Goods.class, params);
                System.out.println("--goodsList.size()--"+goodsList.size());
                for (int i = 0; i < goodsList.size(); i++) {
                    // 数组每一项
                    Goods goods = goodsList.get(i);
                    goods.setCreateTime(new Date());
                    System.out.println("-goods项-"+goods);
                    try {
                        // sql添加操作  会因唯一索引名而报错
                        goodsMapper.insertSelective(goods);
                        successLines++;
                        System.out.println("成功+++");
                    } catch (Exception e) {
                        System.out.println("失败+++");
                        errorLines++;
                        // 异常信息
                        String message = e.getMessage().toLowerCase();
                        // 第几行
                        int lineNumber = i + 1;
                        // 通过索引名判断出错信息    索引名：唯一索引、普通索引
                        if (message.contains("tong_name")) {
                            errorMessage.add("第 " + lineNumber + " 行：商品名已经存在，忽略导入。");
                        } else if (message.contains("tong_url")) {
                            errorMessage.add("第 " + lineNumber + " 行：图片已经存在，忽略导入。");
                        }  else if (message.contains("uniq_name")) {
                            errorMessage.add("第 " + lineNumber + " 行：违反表唯一性约束。");
                        } else {
                            errorMessage.add("第 " + lineNumber + " 行：未知错误，忽略导入");
                            // 错误日志
                            log.error(e.getMessage(), e);
                        }
                    }

                }
            } catch (Exception e) {
                // 异常信息
                errorMessage.add("发生异常：" + e.getMessage());
                // 错误日志
                log.error(e.getMessage(), e);
            } finally {
                try {
                    // 关闭文件输入流
                    inputStream.close();
                } catch (IOException e) {
                    // 错误日志
                    log.error(e.getMessage(), e);
                }
            }

        }


        System.out.println("--errorLines--"+errorLines);
        System.out.println("--successLines--"+successLines);
        System.out.println("--errorMessage--"+errorMessage);
        // 返回信息
        return ExcelUtils.imporReturnRes(errorLines,successLines,errorMessage);
    }
}
