package com.example.test.util;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.DecimalFormat;

/**
 * @author LJQ
 * @date 2019/7/15
 */
public class FileUtils {
    /**
     * 获取格式化的文件大小
     *
     * @param
     * @return
     */
    public static String getFormattedFileSize(long size) {
        DecimalFormat format = new DecimalFormat("0.#");
        long var1 = 1000000000;
        long var2 = 1000000;
        if (size >= var1) {
            return format.format((((double) size) / 1000000000)) + " GB";
        } else if (size >= var2) {
            return format.format((((double) size) / 1000000)) + " MB";
        } else {
            return format.format((((double) size) / 1000)) + " KB";
        }
    }

    /**
     * 获取真实Ip
     * @param request
     * @return
     */
    public static String getRemortIP(HttpServletRequest request) {

        String header = "x-forwarded-for";
        if (request.getHeader(header) == null) {

            return request.getRemoteAddr();

        }

        return request.getHeader("x-forwarded-for");

    }

    /**
     * 删除文件
     *
     * @param strFilePath
     * @return
     */
    public static boolean removeFile(String strFilePath) throws IOException {
        boolean result = false;
        if (strFilePath == null || "".equals(strFilePath)) {
            return result;
        }
        try {
            File file = new File(strFilePath);
            if (file.isFile() && file.exists()) {
                result = file.delete();
                if (result == Boolean.TRUE) {
                    System.out.println("[REMOVE_FILE: " + strFilePath + " 删除成功!]");
                } else {
                    System.out.println("[REMOVE_FILE: " + strFilePath + " 删除失败]");
                }
            }
            return result;
        }
        finally {
            // do nothing ,just throw Exception
        }
    }

    /**
     * 创建文件夹 递归方式
     * @param folderName
     */
    public static void createFolder(String folderName) {

        File myFolderPath = new File(folderName);
        if (myFolderPath.exists()) {
            return ;
        }
        try {
            if (myFolderPath.getParentFile().exists()) {
                myFolderPath.mkdir();
            }
            else {
                createFolder(myFolderPath.getParent());
                myFolderPath.mkdir();
            }
        }
        catch (Exception e) {
            System.out.println("新建目录操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 写入文件 FileInputStream
     * @param fileName
     * @param is
     * @throws IOException
     */
    public static void saveFile(String fileName, InputStream is) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        try {
            int bytesWritten = 0;
            int byteCount = 0;
            int totalSize = is.available();
            byte[] bytes = new byte[1024];
            while ((byteCount = is.read(bytes)) != -1)
            {
                fos.write(bytes, 0, byteCount);
                bytesWritten += byteCount;
            }
            if (bytesWritten != totalSize) {
                throw new EOFException("未知的EOF标志位或提前遇见EOF标志位");
            }
            System.out.println("保存文件成功");
        }
        finally {
            fos.close();
            is.close();
        }
    }
}
