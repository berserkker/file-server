package com.app.fileserver.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.fileserver.dao.FileInfoMapper;
import com.app.fileserver.entity.FileInfo;

@Service
public class FileInfoServiceImpl implements FileInfoService {
    @Autowired
    private FileInfoMapper fileInfoMapper;

    /**
     * 保存用户文件
     *
     * @param fileInfo
     * @return String 文件uuid
     */
    @Override
    public String saveFile(FileInfo fileInfo) {
        String uuid = UUID.randomUUID().toString();
        fileInfo.setUuid(uuid);
        int count = fileInfoMapper.insertSelective(fileInfo);
        if (count > 0) {
            return uuid;
        }
        return "";
    }

    /**
     * 查找文件
     *
     * @param uuid
     */
    @Override
    public FileInfo getFileByUUID(String uuid) {
        FileInfo fileInfo = fileInfoMapper.getFileByUUID(uuid);
        return fileInfo;
    }
}
