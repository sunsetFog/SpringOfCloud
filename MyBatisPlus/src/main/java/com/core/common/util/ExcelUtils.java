package com.core.common.util;

import com.alibaba.fastjson.JSONObject;
import com.upload.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * study: jeecgboot导出
 * 导出excel工具类
 *
 * @author lizhou
 */
@Slf4j
@Component// stydy: @value
public class ExcelUtils {

    private static String basePath;// @Value注解不能注入static修饰   stydy: @value
    // stydy: @value
    @Value("${rafael.file.path}")
    public void setBasePath(String path) {
        System.out.println("大熊猫"+path);
        ExcelUtils.basePath = path;
    }

    /**
     * 导出excel
     * @param title   文件标题
     * @param clazz   实体类型
     * @param exportList 导出数据
     * @param <T> 泛型
     * @return
     */
    public static <T> ModelAndView export(String title, Class<T> clazz, List<T> exportList) {
        System.out.println("--酷酷酷--"+basePath);
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, title);
        //导出实体类
        mv.addObject(NormalExcelConstants.CLASS, clazz);
        //导出作者信息、文件上传根目录 设置
        ExportParams exportParams = new ExportParams(title, "导出人:rafael", "导出信息");
        exportParams.setImageBasePath(basePath);
        mv.addObject(NormalExcelConstants.PARAMS, exportParams);
        //导出数据
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }


    public static Result<?> imporReturnRes(int errorLines, int successLines, List<String> errorMessage) throws IOException {
        if (errorLines == 0) {// 全部导入成功
            return Result.ok("共" + successLines + "行数据全部导入成功！");
        } else {// 导出有错误
            JSONObject result = new JSONObject(5);
            int totalCount = successLines + errorLines;
            result.put("totalCount", totalCount);
            result.put("errorCount", errorLines);
            result.put("successCount", successLines);
            result.put("msg", "总上传行数：" + totalCount + "，已导入行数：" + successLines + "，错误行数：" + errorLines);


            HashMap<String, String> map = ExcelUtils.saveErrorTxtByList(errorMessage, "goodsImportExcelErrorLog");
            result.put("fileUrl", map.get("fileUrl"));
            result.put("fileName", map.get("fileName"));


            Result res = Result.ok(result);
            res.setCode(201);
            res.setMessage("文件导入成功，但有错误。");
            return res;
        }
    }


    public static HashMap<String, String> saveErrorTxtByList(List<String> msg, String name) {
        // File.separator相当于 ' \  '
        System.out.println("--basePath--"+basePath);
        File catalogue = new File(basePath);
        if (!catalogue.exists()) {// 判断当前目录是否存在
            catalogue.mkdirs();// 目录不存在，需要创建
        }

        name += Math.round(Math.random() * 10000);
        String saveFilePath = basePath + name + ".txt";
        System.out.println("--saveFilePath--"+saveFilePath);
        // userImportExcelErrorLog202211242315397380

        try {
            //封装目的地
            BufferedWriter bw = new BufferedWriter(new FileWriter(saveFilePath));
            //遍历集合
            for (String s : msg) {
                //写数据
                if (s.indexOf("_") > 0) {
                    String[] arr = s.split("_");
                    bw.write("第" + arr[0] + "行:" + arr[1]);
                } else {
                    bw.write(s);
                }
                //bw.newLine();
                bw.write("\r\n");
            }
            //释放资源
            bw.flush();
            bw.close();
        } catch (Exception e) {
            log.info("excel导入生成错误日志文件异常:" + e.getMessage());
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("fileName", name);
        map.put("fileUrl", "http://localhost:8060/sky/downloadFile?filename=" + name + ".txt");
        return map;
    }

}
