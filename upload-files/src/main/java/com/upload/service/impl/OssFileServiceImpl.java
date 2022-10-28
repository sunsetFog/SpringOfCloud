package com.upload.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upload.common.util.CommonUtils;
import com.upload.common.util.OssBootUtil;
import com.upload.entity.OssFile;
import com.upload.mapper.OssFileMapper;
import com.upload.service.IOssFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: OSS云存储实现类
 * @author: jeecg-boot
 */
@Slf4j
//@Service("ossFileService")
@Service
public class OssFileServiceImpl extends ServiceImpl<OssFileMapper, OssFile> implements IOssFileService {

	@Override
	public void upload(MultipartFile multipartFile) throws Exception {
		String fileName = multipartFile.getOriginalFilename();
		fileName = CommonUtils.getFileName(fileName);
		OssFile ossFile = new OssFile();
		ossFile.setFileName(fileName);
		String url = OssBootUtil.upload(multipartFile,"upload/test");
		System.out.println("==url01=="+url);// https://rafael.oss-cn-shenzhen.aliyuncs.com/upload/test/u354_1666938465690.png
		System.out.println("==url02=="+OssBootUtil.getOriginalUrl(url));
		//update-begin--Author:scott  Date:20201227 for：JT-361【文件预览】阿里云原生域名可以文件预览，自己映射域名kkfileview提示文件下载失败-------------------
		// 返回阿里云原生域名前缀URL
		ossFile.setUrl(OssBootUtil.getOriginalUrl(url));
		//update-end--Author:scott  Date:20201227 for：JT-361【文件预览】阿里云原生域名可以文件预览，自己映射域名kkfileview提示文件下载失败-------------------
		this.save(ossFile);
	}

	@Override
	public boolean delete(OssFile ossFile) {
		try {
			this.removeById(ossFile.getId());
			OssBootUtil.deleteUrl(ossFile.getUrl());
		}
		catch (Exception ex) {
			log.error(ex.getMessage(),ex);
			return false;
		}
		return true;
	}

}
