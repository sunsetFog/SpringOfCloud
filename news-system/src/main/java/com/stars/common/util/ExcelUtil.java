package com.stars.common.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.stars.pojo.ExcelStudent;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * study: EasyExcel导出导入
 */
public class ExcelUtil {
    public static void writerExcel(HttpServletResponse response, List<ExcelStudent> list) throws IOException {
        // 输出流传EasyExcel
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
        // 定义excel工作表对象，0指第一张表，sheet是表名，表头实体类
        WriteSheet sheet = EasyExcel.writerSheet(0, "sheet").head(ExcelStudent.class).build();
        // excel工作表写入数据
        excelWriter.write(list, sheet);
        // 关闭输出流
        excelWriter.finish();
    }
}
