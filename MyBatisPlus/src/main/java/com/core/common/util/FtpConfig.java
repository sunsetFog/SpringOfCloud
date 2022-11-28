package com.core.common.util;

/**
 * study: Ftp
 * ftp服务器配置实体类
 *
 * @author wk
 *
 */
public class FtpConfig {

    /**
     * 获取IP地址
     */
    private String FTP_ADDRESS = "服务器ip地址";

    /**
     * 端口号
     */
    private String FTP_PORT = "21";

    /**
     * 用户名
     */
    private String FTP_USERNAME = "ftp用户名";

    /**
     * 密码
     */
    private String FTP_PASSWORD = "ftp用户密码";

    /**
     * 基本路径，用户图片
     */
    private String FTP_BASEPATH = "/home/ftptest/tenement/house_images";

    /**
     * 下载地址地基础url，这个是配置的图片服务器的地址,最后访问图片时候，需要用该基础地址
     */
    private String IMAGE_BASE_URL = "url";

    public String getFTP_ADDRESS() {
        return FTP_ADDRESS;
    }

    public void setFTP_ADDRESS(String fTP_ADDRESS) {
        FTP_ADDRESS = fTP_ADDRESS;
    }

    public String getFTP_PORT() {
        return FTP_PORT;
    }

    public void setFTP_PORT(String fTP_PORT) {
        FTP_PORT = fTP_PORT;
    }

    public String getFTP_USERNAME() {
        return FTP_USERNAME;
    }

    public void setFTP_USERNAME(String fTP_USERNAME) {
        FTP_USERNAME = fTP_USERNAME;
    }

    public String getFTP_PASSWORD() {
        return FTP_PASSWORD;
    }

    public void setFTP_PASSWORD(String fTP_PASSWORD) {
        FTP_PASSWORD = fTP_PASSWORD;
    }



    public String getIMAGE_BASE_URL() {
        return IMAGE_BASE_URL;
    }

    public void setIMAGE_BASE_URL(String iMAGE_BASE_URL) {
        IMAGE_BASE_URL = iMAGE_BASE_URL;
    }

    public String getFTP_BASEPATH() {
        return FTP_BASEPATH;
    }

    public void setFTP_BASEPATH(String fTP_BASEPATH) {
        FTP_BASEPATH = fTP_BASEPATH;
    }

}
