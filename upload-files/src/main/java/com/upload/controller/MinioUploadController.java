package com.upload.controller;

import com.upload.common.Result;
import com.upload.common.exception.JeecgBootException;
import com.upload.common.util.CommonUtils;
import com.upload.common.util.MinioUtil;
import com.upload.common.util.oConvertUtils;
import com.upload.entity.OssFile;
import com.upload.service.IOssFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * minio文件上传示例
 * @author: jeecg-boot
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/sys/upload")
public class MinioUploadController {
    @Autowired
    private IOssFileService ossFileService;

    /**
     * 上传
     * @param request
     */
    @PostMapping(value = "/uploadMinio")
    public Result<?> uploadMinio(HttpServletRequest request) throws Exception {
        Result<?> result = new Result<>();
        // 文件保存目录
        String bizPath = request.getParameter("biz");
        System.out.println("==biz=="+bizPath);

        //LOWCOD-2580 sys/common/upload接口存在任意文件上传漏洞
        boolean flag = oConvertUtils.isNotEmpty(bizPath) && (bizPath.contains("../") || bizPath.contains("..\\"));
        if (flag) {
            throw new JeecgBootException("上传目录bizPath，格式非法！");
        }

        if(!oConvertUtils.isNotEmpty(bizPath)){
            bizPath = "";
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获取上传文件对象
        MultipartFile file = multipartRequest.getFile("file");
        // 获取文件名
        String orgName = file.getOriginalFilename();
        System.out.println("==文件名01=="+orgName);// u353.png
        orgName = CommonUtils.getFileName(orgName);
        System.out.println("==文件名02=="+orgName);
        String fileUrl =  MinioUtil.upload(file,bizPath);
        System.out.println("==文件名路径=="+fileUrl);// http://127.0.0.1:9000/minio-admin/ok/u353_1666921334530.png
        if(!oConvertUtils.isNotEmpty(fileUrl)){
            return Result.error("上传失败,请检查配置信息是否正确!");
        }
        //保存文件信息
        OssFile minioFile = new OssFile();
        minioFile.setFileName(orgName);
        minioFile.setUrl(fileUrl);
        ossFileService.save(minioFile);
        result.setMessage(fileUrl);
        result.setSuccess(true);
        return result;
    }
}
