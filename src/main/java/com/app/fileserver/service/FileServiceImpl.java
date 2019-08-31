package com.app.fileserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.app.fileserver.entity.FileInfo;
import com.app.fileserver.param.UploadResponse;
import com.app.fileserver.param.UploadReturn;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileOperatorService fileOperatorService;
    @Autowired
    private FileInfoService fileInfoService;

    /**
     * 上传文件
     *
     * @param file
     * @param type
     */
    @Override
    public UploadResponse uploadFile(MultipartFile file, String type) {
        //上传文件到服务器
        UploadReturn uploadReturn = fileOperatorService.uploadFile(file);
        if (uploadReturn != null) {
            if (!StringUtils.isEmpty(uploadReturn.getMsg())) {
                return new UploadResponse(uploadReturn.getMsg(), null);
            }
            FileInfo fileInfo = new FileInfo();
            fileInfo.setName(uploadReturn.getName());
            fileInfo.setPath(uploadReturn.getPath());
            fileInfo.setSize(uploadReturn.getSize());
            fileInfo.setSuffix(uploadReturn.getSuffix());
            fileInfo.setType(type);
            //插入文件信息到数据库
            String result = fileInfoService.saveFile(fileInfo);
            if (!StringUtils.isEmpty(result)) {
                return new UploadResponse("上传文件成功！", result);
            }
        }
        return new UploadResponse("上传文件失败！", null);
    }

    /**
     * 根据uuid 获取文件信息
     *
     * @param uuid
     * @return FileInfo 文件信息
     */
    @Override
    public FileInfo getFileByUuid(String uuid) {
        return fileInfoService.getFileByUUID(uuid);
    }
}
