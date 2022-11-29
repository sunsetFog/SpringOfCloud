package com.core.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * study: Ftp
 * SpringBoot：上传图片到Linux服务器 https://www.pianshen.com/article/8644745236/
 * @author wk
 *
 */
public class FtpUtil {

    private static final Log logger = LogFactory.getLog(FtpUtil.class);

    /**
     *
     * @param picNewName   新名+后缀
     * @param picSavePath
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String pictureUploadByConfig(String picNewName, String picSavePath,
                                               InputStream inputStream) throws IOException {
        FtpConfig ftpConfig = new FtpConfig();
        logger.info("【pictureUploadByConfig】");
        System.out.println("ftpConfig==="+ftpConfig);
        String picHttpPath = null;

        boolean flag = uploadFile(ftpConfig.getFTP_ADDRESS(), ftpConfig.getFTP_PORT(), ftpConfig.getFTP_USERNAME(),
                ftpConfig.getFTP_PASSWORD(), ftpConfig.getFTP_BASEPATH(), picSavePath, picNewName, inputStream);

        if (!flag) {
            return picHttpPath;
        }
        picHttpPath = ftpConfig.getIMAGE_BASE_URL() + picSavePath + "/" + picNewName;
        logger.info("【picHttpPath】"+picHttpPath);
        System.out.println("picHttpPath==="+picHttpPath);
        return picHttpPath;
    }

    /**
     * Description: 向FTP服务器上传文件
     *
     * @param host
     *            FTP服务器hostname
     * @param port
     *            FTP服务器端口
     * @param username
     *            FTP登录账号
     * @param password
     *            FTP登录密码
     * @param basePath
     *            FTP服务器基础目录
     * @param filePath
     *            FTP服务器文件存放路径。
     * @param filename
     *            上传到FTP服务器上的文件名
     * @param input
     *            输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String host, String ftpPort, String username, String password, String basePath,
                                     String filePath, String filename, InputStream input) {
        int port = Integer.parseInt(ftpPort);
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            System.out.println("host=="+host);
            System.out.println("port=="+port);
            /**
             * java.net.ConnectException: Connection refused: connect
             * springboot读取sftp文件  http://www.qb5200.com/article/470919.html
             * Linux下开启FTP的21端口   https://cloud.tencent.com/developer/article/1334725?from=15425
             */
            ftp.connect(host, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            // 切换到上传目录
            if (!ftp.changeWorkingDirectory(basePath + filePath)) {
                // 如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = basePath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir))
                        continue;
                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        if (!ftp.makeDirectory(tempPath)) {
                            return result;
                        } else {
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            // 设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();// 这个设置允许被动连接--访问远程ftp时需要
            // 上传文件
            if (!ftp.storeFile(filename, input)) {
                return result;
            }
            input.close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }
}
