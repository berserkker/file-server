package com.app.fileserver.service;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.fileserver.param.UploadReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件操作
 */
@Service
public class FileOperatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileOperatorService.class);
    @Value("${file-upload-dir:/opt/data}")
    private String filePath;
    @Value("${file-upload-suffix:.txt}")
    private String fileSuffix;

    /**
     * 执行文件上传
     */
    public UploadReturn uploadFile(MultipartFile file) {
        //获取可以上传的文件后缀名
        String[] suffixArray = fileSuffix.split(",");
        List<String> suffixList = new ArrayList<>(suffixArray.length);
        Collections.addAll(suffixList, suffixArray);

        try {
            if (file.isEmpty()) {
                return new UploadReturn("文件为空！");
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            LOGGER.info("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            LOGGER.info("文件的后缀名为：" + suffixName);
            if (!suffixList.contains(suffixName)) {
                return new UploadReturn("不能上传该类型的文件！");
            }

            String hashValue = getFileHashValue(file);
            if (hashValue == null) {
                return new UploadReturn("上传文件失败！");
            }
            // 设置文件存储路径,上传到服务器的文件名为文件的hash值
            String path = filePath + getFilePath(hashValue) + "/" + hashValue + suffixName;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            UploadReturn uploadReturn = new UploadReturn();
            uploadReturn.setMsg("");
            uploadReturn.setName(fileName);
            uploadReturn.setPath(path);
            uploadReturn.setSize(file.getSize());
            uploadReturn.setSuffix(suffixName);
            return uploadReturn;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String downloadFile(String path, String name) {
        return null;
    }

    /**
     * 获取文件Hash值
     */
    private String getFileHashValue(MultipartFile file) {
        try {
            byte[] uploadBytes = file.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            String hashString = new BigInteger(1, digest).toString(16);
            return hashString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据文件hash值进行目录打散
     */
    private String getFilePath(String hash) {
        String path = "/";
        //转成二进制1000001111111100001001010
        String bin = Integer.toBinaryString(hash.hashCode());
        //位数 32位
        bin = "0000000000000000000000000000000" + bin;
        bin = bin.substring(bin.length() - 32);

        int b = hash.hashCode() & 0xf;
        bin = Integer.toBinaryString(b);
        bin = "0000000000000000000000000000000" + bin;
        bin = bin.substring(bin.length() - 32);

        //转换成16进制,第一层目录
        String hex = Integer.toHexString(b);
        path += hex;

        int c = (hash.hashCode() >> 4) & 0xf;
        bin = Integer.toBinaryString(c);
        bin = "0000000000000000000000000000000" + bin;
        bin = bin.substring(bin.length() - 32);

        //第二层目录
        hex = Integer.toHexString(c);
        path += "/" + hex;
        return path;
    }
}
