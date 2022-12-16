package com.stars.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * study: 文档预览
 */
@RestController
@CrossOrigin
public class DocumentPreview {
    /**
     * 在线预览文件地址
     */
    @Value("http://${rafael.file-view-domain}/onlinePreview")
    private String onlinePreviewDomain;
    /**
     * 获取文件预览的地址
     * @return
     */
    @GetMapping(value = "/getFileViewDomain")
    public String getFileViewDomain() {
        return onlinePreviewDomain;
    }
}
