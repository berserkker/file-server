package com.app.fileserver.service;

import org.springframework.web.multipart.MultipartFile;

import com.app.fileserver.entity.FileInfo;
import com.app.fileserver.param.UploadResponse;

/**
 * 文件上传下载服务
 */
public interface FileService {

    /**
     * 上传文件
     *
     * @param
     */
    UploadResponse uploadFile(MultipartFile file, String type);

    /**
     * 根据uuid 获取文件信息
     *
     * @param uuid
     * @return FileInfo 文件信息
     */
    FileInfo getFileByUuid(String uuid);
}
