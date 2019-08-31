package com.app.fileserver.service;

import org.springframework.stereotype.Service;

/**
 * 远程接口
 */
@Service
public class RemoteUserServiceImpl implements RemoteUserService {
    /**
     * 验证token是否正确
     *
     * @param token
     */
    @Override
    public boolean checkToken(String token) {
        return true;
    }
}
