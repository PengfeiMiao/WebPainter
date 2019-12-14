package com.example.test.service.impl;


import com.example.test.service.IFileService;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;


/**
 * @author LogicArk
 * @date 2018/11/18
 */
@Service
public class FileService implements IFileService {

    /**
     * Created by luojizhou on 2017/05/18
     * 下载文件 (下载服务器磁盘上任意文件的方法  ，可作为通用方法)
     *
     * @param response
     */
    @Override
    public boolean downloadCommonFileOnServerDisk(String filePath, HttpServletResponse response) {

        if (filePath == null || "".equals(filePath)) {
            return false;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return false;
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream;charset=utf-8");
        try {
            InputStream is = new FileInputStream(file);
            //maybe cause FileNotFoundException
            String fileName = file.getName();

            HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String agent = httpRequest.getHeader("User-Agent");
            String firefox = "firefox";
            if (agent.toLowerCase().indexOf(firefox) > 0) {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            }
            //其他浏览器
            else {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }

            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + "");


            FileCopyUtils.copy(is, response.getOutputStream());
            // maybe cause IOException
            response.flushBuffer();
            is.close();
            response.getOutputStream().close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void downloadAttachmentFile(String attachmentId, HttpServletResponse response) {

    }

    @Override
    public void deleteOneDirectoryOnDiskIfExist(File directory) throws IOException {

    }


}
