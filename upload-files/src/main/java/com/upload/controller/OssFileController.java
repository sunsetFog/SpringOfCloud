package com.upload.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upload.common.Result;
import com.upload.entity.OssFile;
import com.upload.service.IOssFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 云存储示例 DEMO
 * @author: jeecg-boot
 */
@Slf4j
@Controller
@CrossOrigin
@RequestMapping("/sys/oss/file")
public class OssFileController {

    @Autowired
    private IOssFileService ossFileService;

    @ResponseBody
    @GetMapping("/list")
    public Result<IPage<OssFile>> queryPageList(OssFile file,
                                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        Result<IPage<OssFile>> result = new Result<>();
//        QueryWrapper<OssFile> queryWrapper = QueryGenerator.initQueryWrapper(file, req.getParameterMap());
        QueryWrapper<OssFile> queryWrapper = new QueryWrapper<>();
        Page<OssFile> page = new Page<>(pageNo, pageSize);
        IPage<OssFile> pageList = ossFileService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    @ResponseBody
    @PostMapping("/upload")
    //@RequiresRoles("admin")
    public Result upload(@RequestParam("file") MultipartFile multipartFile) {
        Result result = new Result();
        try {
            ossFileService.upload(multipartFile);
            result.success("上传成功！");
        }
        catch (Exception ex) {
            log.info(ex.getMessage(), ex);
            result.error500("上传失败");
        }
        return result;
    }

    @ResponseBody
    @DeleteMapping("/delete")
    public Result delete(@RequestParam(name = "id") String id) {
        Result result = new Result();
        OssFile file = ossFileService.getById(id);
        if (file == null) {
            result.error500("未找到对应实体");
        }else {
            boolean ok = ossFileService.delete(file);
            result.success("删除成功!");
        }
        return result;
    }

    /**
     * 通过id查询.
     */
    @ResponseBody
    @GetMapping("/queryById")
    public Result<OssFile> queryById(@RequestParam(name = "id") String id) {
        Result<OssFile> result = new Result<>();
        OssFile file = ossFileService.getById(id);
        if (file == null) {
            result.error500("未找到对应实体");
        }
        else {
            result.setResult(file);
            result.setSuccess(true);
        }
        return result;
    }

}
