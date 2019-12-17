package com.example.test.service;

import com.example.test.entity.CommonFile;
import com.example.test.finder.OptionFinder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IFileService {

    boolean uploadFile(MultipartFile file, CommonFile commonFile);

    /**
     * Created by luojizhou on 2017/05/18
     * 下载文件 (下载服务器磁盘上任意文件的方法  ，可作为通用方法)
     *
     * @param response
     */
    boolean downloadCommonFileOnServerDisk(String filePath, HttpServletResponse response);

    /**
     * Created by LogicArk on 2019/5/8
     * @description 下载附件
     * @param
     * @returni
     */
    void downloadAttachmentFile(String id, HttpServletResponse response);


    /**
     * Created by LogicArk on 2019/5/8
     * @param
     * @return
     * @description 删除文件夹 (删除服务器磁盘 某一个文件夹 ，如果存在则删除 ，可作为通用方法)
     */
    void deleteOneDirectoryOnDiskIfExist(File directory) throws IOException;

    List<CommonFile> getCommonFileByCreator(String creator);

    CommonFile getCommonFileById(Integer Id);
}
