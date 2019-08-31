package com.app.fileserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.app.fileserver.param.ResultResponse;
import com.app.fileserver.param.UploadResponse;
import com.app.fileserver.service.FileService;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     *
     * @param file 文件
     * @param type 文件类型（不是后缀，而是业务相关的文件分类）
     */
    @PostMapping("/uploadFile")
    public ResultResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String type) {

        UploadResponse uploadResponse = fileService.uploadFile(file, type);
        if (!StringUtils.isEmpty(uploadResponse.getUuid())) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uuid", uploadResponse.getUuid());
            return new ResultResponse(0, uploadResponse.getMsg(), new JSONObject());
        } else {
            return new ResultResponse(1, uploadResponse.getMsg(), null);
        }

    }
}
