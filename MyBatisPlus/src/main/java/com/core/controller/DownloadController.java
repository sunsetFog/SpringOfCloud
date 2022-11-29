package com.core.controller;

import cn.hutool.http.HttpUtil;
import com.core.common.util.FtpUtil;
import com.upload.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@CrossOrigin
@Slf4j
public class DownloadController {

    @Value("${rafael.file.path}")
    private String basePath;
    // study: linux-img
    @Value("${rafael.file.linux}")
    private String serverPath;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/uploadFile")
    public Result<?> uploadFile(MultipartFile file) {
        //file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除
        log.info(file.toString());
        //获得原始文件名称
        String originalFilename = file.getOriginalFilename();
        //截取原始文件名后缀 .jpg  / .png等
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //2、使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;// 3bdfa5ef-528d-41dc-948c-e144e597d0f9
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
     * 文件上传Linux    study: linux-img
     * result: "20221129001123.PNG文件上传Linux成功"   文件找不到  不知是否docker
     * @param file
     * @return
     */
    @PostMapping("/uploadLinux")
    public Result<?> uploadLinux(MultipartFile file) throws IOException {
        //file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除
        log.info(file.toString());
        //1、获得原始文件名称
        String originalFilename = file.getOriginalFilename();
        //1.1、截取原始文件名后缀 .jpg  / .png等
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //2、使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        String dataStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = dataStr + suffix;


        FtpUtil.pictureUploadByConfig(fileName, serverPath, file.getInputStream());


//        //创建一个目录对象
//        File dir = new File(serverPath);
//        //3、判断当前目录是否存在
//        if(!dir.exists()){
//            //目录不存在，需要创建
//            dir.mkdirs();
//        }
//
//        try {
//            //将临时文件转存到指定位置
//            file.transferTo(new File(serverPath + fileName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return Result.ok(fileName+"文件上传Linux成功");
    }

    /**
     * 文件下载
     * 输入流中的数据循环写入到响应输出流中，而不是一次性读取到内存，返回输出流
     * @param filename    goodsImportExcelErrorLog5545.txt
     * @param response
     * @throws Exception
     */
    @GetMapping("/downloadFile")
    public synchronized void downloadFile(String filename , HttpServletResponse response) throws Exception {
        //设置响应的信息
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" +  URLEncoder.encode(filename, "utf8"));
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        //设置浏览器接受类型为流
        response.setContentType("application/octet-stream;charset=UTF-8");
        //设置文件类型
        //response.setContentType("image/jpeg");



        //确保下载路径包含download关键字（确保下载内容，在download文件夹下）
        System.out.println("--filename--"+filename);
        if(!filename.contains("ImportExcelErrorLog")) {
            throw new Exception("下载路径不合法。");
        }
        File file = new File(basePath+filename);

        //获取文件后缀
        int lastIndex = filename.lastIndexOf(".");
        String extension = filename.substring(lastIndex + 1);
        System.out.println("--extension--"+extension);


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

    }

    /**
     * 文件下载
     * 文件以流的形式一次性读取到内存，返回输出流
     * @param filename    goodsImportExcelErrorLog5545.txt
     * @param response
     * @throws Exception
     */
    @GetMapping("/downloadFile2")
    public synchronized void downloadFile2(String filename , HttpServletResponse response) throws Exception {
        //设置响应的信息
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" +  URLEncoder.encode(filename, "utf8"));
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        //设置浏览器接受类型为流
        response.setContentType("application/octet-stream;charset=UTF-8");
        //设置文件类型
        //response.setContentType("image/jpeg");


        File file = new File(basePath+filename);
        //获取文件后缀
//        int lastIndex = filename.lastIndexOf(".");
//        String extension = filename.substring(lastIndex + 1);
//        System.out.println("--extension--"+extension);

        // 将文件写入输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        byte[] buffer = new byte[bufferedInputStream.available()];
        bufferedInputStream.read(buffer);
        bufferedInputStream.close();

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
        bufferedOutputStream.write(buffer);
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }
    /**
     * springboot项目下载网络文件到本地,返回网络路径   https://blog.csdn.net/qq_27348837/article/details/104690215
     * 预览图片路径转下载图片路径
     * @param fileUrl  http://localhost:8062/sky/img/avatorImages/1647277665830invoice.png
     */
    @GetMapping("/previewToDownload")
    public synchronized String previewToDownload(String fileUrl) throws FileNotFoundException {
        // 后缀
        String suffix = fileUrl.substring(fileUrl.lastIndexOf("."));
        System.out.println("后缀="+suffix);
        // 文件名
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        System.out.println("文件名="+fileName);
        // 下载路径
        String download_path = "profile/" + fileName + suffix;
        // 保存图片到服务器的路径
        String save_path = basePath + fileName + suffix;
        System.out.println("保存路径=="+save_path);
        // hutool方式：网络文件保存到服务器
        HttpUtil.downloadFile(fileUrl, save_path);
        return download_path;
    }
}
