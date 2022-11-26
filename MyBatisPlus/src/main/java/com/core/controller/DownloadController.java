package com.core.controller;

import com.upload.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

@RestController
@CrossOrigin
@Slf4j
public class DownloadController {

    @Value("${rafael.file.path}")
    private String basePath;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/uploadFile")
    public Result<?> uploadFile(MultipartFile file) {
        //file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除
        log.info(file.toString());
        //1、获得原始文件名称
        String originalFilename = file.getOriginalFilename();
        //1.1、截取原始文件名后缀 .jpg  / .png等
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //2、使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;
        //创建一个目录对象
        File dir = new File(basePath);
        //3、判断当前目录是否存在
        if(!dir.exists()){
            //目录不存在，需要创建
            dir.mkdirs();
        }

        try {
            //将临时文件转存到指定位置
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.ok(fileName+"文件上传成功");
    }

    /**
     * 文件下载
     * @param filename
     * @param response
     * @throws Exception
     */
    @GetMapping("/downloadFile")
    public synchronized void downloadFile(String filename , HttpServletResponse response) throws Exception {
        //确保下载路径包含download关键字（确保下载内容，在download文件夹下）
        System.out.println("--filename--"+filename);
        if(filename.contains("ImportExcelErrorLog")) {
            File file = new File(basePath+filename);

            //获取文件后缀
            int lastIndex = filename.lastIndexOf(".");
            String extension = filename.substring(lastIndex + 1);
            System.out.println("--extension--"+extension);
            //设置响应的信息
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" +  URLEncoder.encode(filename, "utf8"));
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            //设置浏览器接受类型为流
            response.setContentType("application/octet-stream;charset=UTF-8");
            //设置文件类型
//            response.setContentType("image/jpeg");

            FileInputStream fileInputStream = null;
            OutputStream outputStream = null;

            try{
                fileInputStream = new FileInputStream(file);
                // 将文件写入输入流
                outputStream = response.getOutputStream();

                if("doc".equals(extension)) {
                    //doc文件就以HWPFDocument创建
                    HWPFDocument doc = new HWPFDocument(fileInputStream);
                    //写入
                    doc.write(outputStream);
                    //关闭对象中的流
                    doc.close();
                }else if("docx".equals(extension)) {
                    //docx文件就以XWPFDocument创建
                    XWPFDocument docx = new XWPFDocument(fileInputStream);
                    docx.write(outputStream);
                    docx.close();
                } else {
                    //其他类型的文件，按照普通文件传输 如（zip、rar等压缩包）
                    int len;
                    //一次传输1M大小字节
                    byte[] bytes = new byte[1024];
                    while ((len = fileInputStream.read(bytes)) != -1) {
                        outputStream.write(bytes  , 0 , len);
                        outputStream.flush();
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if(outputStream != null) {
                        outputStream.close();
                    }
                    if(fileInputStream != null) {
                        fileInputStream.close();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        } else {
            throw new Exception("下载路径不合法。");
        }

    }
}
