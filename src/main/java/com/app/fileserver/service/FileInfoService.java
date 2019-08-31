package com.app.fileserver.service;

import com.app.fileserver.entity.FileInfo;

public interface FileInfoService {

    /**
     * 保存用户文件
     * @return String 文件uuid
     * */
    String saveFile(FileInfo fileInfo);

    /**
     * 查找文件
     * */
    FileInfo getFileByUUID(String uuid);


}
