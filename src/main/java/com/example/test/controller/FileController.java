package com.example.test.controller;

import com.example.test.bean.RespBean;
import com.example.test.util.StringUtil;
import com.google.gson.Gson;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author mpf
 * @date 2019/12/9
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping("/upload/image")
    public RespBean uploadImage(HttpServletRequest request) {
        /** 从request中获取 */
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        String filename = request.getParameter("filename");
        String creator = request.getParameter("creator");

        String result_msg = "";//上传结果信息

        filename = StringUtil.isBlank(filename) ? file.getOriginalFilename() : filename;

        if (file.getSize() / 1000 > 100) {
            result_msg = "图片大小不能超过100KB";
        } else {
            //判断上传文件格式
            String fileType = file.getContentType();
            if (fileType.equals("image/jpeg") || fileType.equals("image/png") || fileType.equals("image/jpeg")) {
                // 要上传的目标文件存放的绝对路径
                final String localPath = "D://file/upload/image";
                //上传后保存的文件名(需要防止图片重名导致的文件覆盖)
                //拼接文件名
                filename += ("." + file.getContentType().split("/")[1]);
                System.out.println(new Gson().toJson(file));
                File tempFileParentFolder = new File(localPath).getParentFile();
                if (!tempFileParentFolder.exists()) {
                    tempFileParentFolder.mkdirs();
                }
                try {
                    file.transferTo(new File(localPath + File.separator + filename));
                } catch (IOException e) {
                    e.printStackTrace();
                    return RespBean.sendSuccessMessage("图片上传失败");
                }
                result_msg = "图片上传成功";
            } else {
                result_msg = "图片格式不正确";
            }
        }

        return RespBean.sendSuccessMessage(result_msg);
    }
}