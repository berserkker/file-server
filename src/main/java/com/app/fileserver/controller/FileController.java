package com.app.fileserver.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.app.fileserver.entity.FileInfo;
import com.app.fileserver.param.ResultResponse;
import com.app.fileserver.param.UploadResponse;
import com.app.fileserver.service.FileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;

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
            return new ResultResponse(0, uploadResponse.getMsg(), jsonObject);
        } else {
            return new ResultResponse(1, uploadResponse.getMsg(), null);
        }
    }

    @GetMapping("/downloadFile/{uuid}")
    public String downloadFile(@PathVariable String uuid, HttpServletRequest request,
                               HttpServletResponse response) {
        FileInfo fileInfo = fileService.getFileByUuid(uuid);
        if (fileInfo != null && !StringUtils.isEmpty(fileInfo.getPath())) {
            //设置文件路径
            File file = new File(fileInfo.getPath());
            if (file.exists()) {
                response.setCharacterEncoding(request.getCharacterEncoding());
                response.setContentType("application/octet-stream");
//                response.setContentType("image/"+fileInfo.getSuffix());
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    response.setHeader("Content-Disposition", "attachment; filename=" + fileInfo.getName());
                    IOUtils.copy(fis, response.getOutputStream());
                    response.flushBuffer();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return JSONObject.toJSONString(new ResultResponse(1, "not found this file！", null));
    }
}
