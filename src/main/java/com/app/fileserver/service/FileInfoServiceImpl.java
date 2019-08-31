package com.app.fileserver.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.app.fileserver.entity.FileInfo;

@Service
public class FileInfoServiceImpl implements FileInfoService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 保存用户文件
     *
     * @param fileInfo
     * @return String 文件uuid
     */
    @Override
    public String saveFile(FileInfo fileInfo) {
        String uuid = UUID.randomUUID().toString();
        int count = jdbcTemplate.update("insert into fileinfo(name,size,type,path,suffix,uuid) values(?,?,?,?,?,?)",
                fileInfo.getName(), fileInfo.getSize(), fileInfo.getType(),
                fileInfo.getPath(), fileInfo.getSuffix(), uuid);
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
        RowMapper<FileInfo> rowMapper = new BeanPropertyRowMapper<>(FileInfo.class);
        return jdbcTemplate.queryForObject("select * from fileinfo where uuid = ?", rowMapper, uuid);
    }
}
