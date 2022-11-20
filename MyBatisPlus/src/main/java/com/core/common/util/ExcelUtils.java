package com.core.common.util;

import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * study: jeecgboot导出
 * 导出excel工具类
 *
 * @author lizhou
 */
public class ExcelUtils {
    @Value("${jeecg.path.upload}")
    private static String upLoadPath;

    /**
     * 导出excel
     * @param title   文件标题
     * @param clazz   实体类型
     * @param exportList 导出数据
     * @param <T> 泛型
     * @return
     */
    public static <T> ModelAndView export(String title, Class<T> clazz, List<T> exportList) {
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, title);
        //导出实体类
        mv.addObject(NormalExcelConstants.CLASS, clazz);
        //导出作者信息、文件上传根目录 设置
        ExportParams exportParams = new ExportParams(title, "导出人:rafael", "导出信息");
        exportParams.setImageBasePath(upLoadPath);
        mv.addObject(NormalExcelConstants.PARAMS, exportParams);
        //导出数据
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }

}
