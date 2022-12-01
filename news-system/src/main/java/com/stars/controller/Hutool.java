package com.stars.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/*
study: hutool
官网，搜hutool：https://www.hutool.cn/
hutool之Convert类型转换类：
*/
@RestController
@CrossOrigin
public class Hutool {
    @GetMapping("/typeConversion")
    public String typeConversion() {
        int a = 1;
        String aStr = Convert.toStr(a);
        System.out.println("--Convert数字转字符串--"+aStr);// 1

        long[] b = {1, 2, 3, 4};
        String bStr = Convert.toStr(b);
        System.out.println("--Convert数组转字符串--"+bStr);// [1, 2, 3, 4]

        String[] c = {"1", "2", "3"};
        Integer[] integers = Convert.toIntArray(c);
        System.out.println("--Convert之String数组转Integer数组--"+integers);

        String d = "2022-12-12";
        try {
            Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(d);
            System.out.println("--原生字符串转日期--"+parse);
        } catch (ParseException e) {

        } finally {

        }

        Date date1 = Convert.toDate(d);
        System.out.println("--Convert字符串转日期1--"+date1);
        String e = "2022/12/12";
        Date date2 = Convert.toDate(e);
        System.out.println("--Convert字符串转日期2--"+date2);

        String[] f = {"hello", "xiaoming"};
        List<String> strings = Arrays.asList(f);
        strings.forEach(item -> System.out.println("-原生数组转集合1-"+item));
        List<String> objects = (List<String>) Convert.toList(f);
        objects.forEach(item -> System.out.println("-Convert数组转集合2-"+item));
        int[] t = {21, 24};// int类型，原生转不了集合，Convert可以转集合
        return "OKOK!";
    }
    // 原生和IoUtile文件拷贝
    @GetMapping("/isFlow")
    public String isFlow() throws IOException {
        File file1 = new File("C:\\Users\\USER\\Desktop\\sunsetFog\\SpringBoot\\src\\main\\resources\\banner.txt");
        File file2 = new File("C:\\Users\\USER\\Desktop\\sunsetFog\\SpringBoot\\src\\main\\resources\\banner222.txt");
        // 输入流
        FileInputStream fileInputStream = new FileInputStream(file1);
        // 输出流
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        // 为了读取读完，定义缓冲区
//        byte[] bytes = new byte[1024];
//        int len = 0;
//        while (true) {
//            // 读取
//            len = fileInputStream.read(bytes);
//            if (len == -1) break;
//            // 写入
//            fileOutputStream.write(bytes, 0, len);
//        }
//        fileInputStream.close();
//        fileOutputStream.close();
        IoUtil.copy(fileInputStream, fileOutputStream);
        return "OKOK";
    }
}
