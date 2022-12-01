package com.stars.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
/**
 * study: uploadImg
 */
@Service
public class UploadService {
    @Value("${rafael.file.basePath}")
    private String basePath;

    private static final List<String> CONTENT_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif");

    public HashMap<String, String> picrureModify(MultipartFile avatorFile) {
        HashMap<String, String> Sites = new HashMap<>();



        // 校验文件的类型
//        String contentType = avatorFile.getContentType();
//        if (!CONTENT_TYPES.contains(contentType)){
//            Sites.put("msg", "文件类型不合法");
//            return Sites;
//        }



        if (avatorFile.isEmpty()) {
            Sites.put("msg", "文件上传失败！");
            return Sites;
        }
        // 获得原始文件名称
        String originalFilename = avatorFile.getOriginalFilename();
        // 后缀 .jpg  / .png等
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 文件名
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        System.out.println("-fileName-: "+fileName);
//        // 项目路径
//        String PROJECT_PATH = System.getProperty("user.dir");
//        // 斜杆   \
//        String inclined_rod = System.getProperty("file.separator");
//
//        System.out.println("-111-: "+PROJECT_PATH);
//        System.out.println("-222-: "+inclined_rod);

        //创建一个目录对象
        File dir = new File(basePath);
        //3、判断当前目录是否存在
        if(!dir.exists()){
            //目录不存在，需要创建
            dir.mkdirs();
        }
        // 保存图片路径
        String save_path = basePath + fileName + suffix;
        System.out.println("保存图片路径"+save_path);
        // 预览图片地址
        String preview_path = "profile/" + fileName + suffix;
        System.out.println("预览图片地址"+preview_path);

        try {
            // 获取字节流
//            byte[] bytes = avatorFile.getBytes();
//            System.out.println("-bytes-: "+bytes);

            // 校验文件的内容
//            BufferedImage bufferedImage = ImageIO.read(avatorFile.getInputStream());
//            if (bufferedImage == null){
//                Sites.put("msg", "文件内容不合法");
//                return Sites;
//            }

            // 保存到服务器
            avatorFile.transferTo(new File(save_path));

        } catch (IOException e) {
            Sites.put("msg", "上传失败" + e.getMessage());
            return Sites;
        } finally {

        }
        Sites.put("avator", preview_path);
        Sites.put("msg", "上传成功");
        return Sites;
    }
}
