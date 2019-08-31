package com.app.fileserver.param;

public class UploadResponse {
    /**
     * 上传信息
     */
    private String msg;
    /**
     * 文件uuid
     */
    private String uuid;

    public UploadResponse(String msg, String uuid) {
        this.msg = msg;
        this.uuid = uuid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
