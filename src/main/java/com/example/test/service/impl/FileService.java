package com.example.test.service.impl;

import com.example.test.entity.CommonFile;
import com.example.test.mapper.FileMapper;
import com.example.test.service.IFileService;
import com.example.test.staticobject.CommonStatic;
import com.example.test.util.StringUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

@Service
public class FileService implements IFileService {

    @Autowired
    private FileMapper fileMapper;

    @Override
    public boolean uploadFile(MultipartFile file, CommonFile commonFile) {

        //上传后保存的文件名(需要防止图片重名导致的文件覆盖)
        //拼接文件名
        String filename = StringUtil.isBlank(commonFile.getFilename()) ?
                file.getOriginalFilename() : commonFile.getFilename();
        filename += ("." + file.getContentType().split("/")[1]);
        System.out.println(new Gson().toJson(file));
        File tempFileParentFolder = new File(CommonStatic.LOCAL_PATH);
        if (!tempFileParentFolder.exists()) {
            tempFileParentFolder.mkdirs();
        }
        String filepath = CommonStatic.LOCAL_PATH + File.separator + filename;
        try {
            file.transferTo(new File(filepath));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        commonFile.setFilename(filename);
        commonFile.setFilepath(filepath);
        commonFile.setUploadtime(new Date());
        //保存文件信息
        fileMapper.save(commonFile);
        return true;
    }

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

    @Override
    public List<CommonFile> getCommonFileByCreator(String creator) {
        CommonFile commonFile = new CommonFile();
        commonFile.setCreator(creator);
        return fileMapper.selectByCriteria(commonFile);
    }

    @Override
    public CommonFile getCommonFileById(Integer id) {
        return fileMapper.selectByPrimaryKey(id);
    }


}
