package com.app.fileserver.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public void downloadFile(@PathVariable String uuid, HttpServletResponse response) {
        FileInfo fileInfo = fileService.getFileByUuid(uuid);
        if (fileInfo != null) {
            try (
                    InputStream inputStream = new FileInputStream(new File(fileInfo.getPath()));
                    OutputStream outputStream = response.getOutputStream()
            ) {
                // 配置文件下载
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                // 下载文件能正常显示中文
                response.setHeader("Content-Disposition", "attachment;filename=" +
                        URLEncoder.encode(fileInfo.getName(), "UTF-8"));
                //把输入流copy到输出流
                IOUtils.copy(inputStream, outputStream);
                outputStream.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 返回文件流
     */
    @RequestMapping(value = "/getFileStream/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadFile(@PathVariable String uuid)
            throws IOException {
        FileInfo fileInfo = fileService.getFileByUuid(uuid);
        if (fileInfo != null) {
            /*FileSystemResource file = new FileSystemResource(fileInfo.getPath());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"",
                    URLEncoder.encode(fileInfo.getName(), "UTF-8")));
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(file.contentLength())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(file.getInputStream()));*/
            byte[] body = null;
            FileSystemResource file = new FileSystemResource(fileInfo.getPath());
            InputStream in = file.getInputStream();

            body = new byte[in.available()];
            in.read(body);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"",
                    URLEncoder.encode(fileInfo.getName(), "UTF-8")));
            HttpStatus statusCode = HttpStatus.OK;

            ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);
            return response;
        }
        return null;
    }
}
