package com.upload.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.upload.entity.OssFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: OOS云存储service接口
 * @author: jeecg-boot
 */
public interface IOssFileService extends IService<OssFile> {

    /**
     * oss文件上传
     * @param multipartFile
     * @throws =IOException
     */
	void upload(MultipartFile multipartFile) throws Exception;

    /**
     * oss文件删除
     * @param ossFile OSSFile对象
     * @return
     */
	boolean delete(OssFile ossFile);

}
