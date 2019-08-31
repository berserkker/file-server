package com.app.fileserver.service;

public interface RemoteUserService {
    /**
     * 验证token是否正确
     * */
    boolean checkToken(String token);
}
