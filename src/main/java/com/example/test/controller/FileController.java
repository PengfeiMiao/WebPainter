package com.example.test.controller;

import com.example.test.bean.RespBean;
import com.example.test.entity.CommonFile;
import com.example.test.service.impl.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mpf
 * @date 2019/12/9
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload/image")
    public RespBean uploadImage(HttpServletRequest request) {
        /** 从request中获取 */
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        String filename = request.getParameter("filename");
        String creator = request.getParameter("creator");
        //上传结果信息
        String resultMsg;
        if (file == null) {
            resultMsg = "上传图片为空";
        } else if (file.getSize() / 1000 > 1024) {
            resultMsg = "图片大小不能超过1MB";
        } else {
            //判断上传文件格式
            String fileType = file.getContentType();
            if (fileType.equals("image/jpeg") || fileType.equals("image/png") || fileType.equals("image/jpg")) {
                CommonFile commonFile = new CommonFile();
                commonFile.setFilename(filename);
                commonFile.setCreator(creator);
                boolean flag = fileService.uploadFile(file, commonFile);
                if (flag) {
                    return RespBean.sendSuccessMessage("图片上传成功");
                } else {
                    resultMsg = "图片上传失败";
                }
            } else {
                resultMsg = "图片格式不正确";
            }
        }

        return RespBean.sendErrorMessage(resultMsg);
    }

    /**
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/downloadFileById")
    public RespBean downloadFileById(Integer id, HttpServletResponse response) {

        CommonFile selected = fileService.getCommonFileById(id);

        if (selected != null && selected.getFilepath() != null) {
            fileService.downloadCommonFileOnServerDisk(selected.getFilepath(), response);
            return RespBean.sendSuccessMessage();
        } else {
            return RespBean.sendErrorMessage();
        }
    }
}