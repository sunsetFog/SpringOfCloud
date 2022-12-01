package com.stars.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

// SpringBoot实现文件下载的几种方式
// springboot下载网络图片到本地及Linux服务器(超详细)
// https://blog.csdn.net/Alan_ran/article/details/123871594
@RestController
@CrossOrigin
public class Download {
    /**
     * 网络文件保存到服务器，没有返回
     * @param netAddress 网络地址：百度动漫图片》打开控制台》元素指定img标签》右键》复制链接地址  或  图片右击》复制图片地址
     *                   下载地址：下载按钮的a标签
     */
    @RequestMapping("/netDownloadLocal")
    public void downloadNet(String netAddress) throws IOException {
        URL url = new URL(netAddress);
        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();

        String dataStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 20221128153754
        FileOutputStream fileOutputStream = new FileOutputStream("D:/profile/cun/"+dataStr+".png");

        int byteread;
        byte[] buffer = new byte[1024];
        while ((byteread = inputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, byteread);
        }
        fileOutputStream.close();
    }



    /**
     * 下载网路文件
     * 网络文件，返回输出流
     * @param netAddress
     * @param isOnLine
     * @param response
     */
    @RequestMapping("/netDownLoadNet")
    public void netDownLoadNet(String netAddress, boolean isOnLine, HttpServletResponse response) throws Exception {

        URL url = new URL(netAddress);
        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();

        //response.reset();// 报错200响应不了
        response.setContentType(conn.getContentType());
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");


        String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 20221128153754
        if (isOnLine) {
            // 在线打开方式 文件名应该编码成UTF-8
            response.setHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(filename, "UTF-8"));
        } else {
            //纯下载方式 文件名应该编码成UTF-8
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        }

        byte[] buffer = new byte[1024];
        int len;
        OutputStream outputStream = response.getOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        inputStream.close();
    }

}
