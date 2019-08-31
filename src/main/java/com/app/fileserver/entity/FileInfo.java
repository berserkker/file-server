package com.app.fileserver.entity;

/**
 * 文件属性表fileinfo
 */
public class FileInfo {
    private Integer id;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件大小
     */
    private long size;
    /**
     * 文件路径
     */
    private String path;
    /**
     * 文件后缀
     */
    private String suffix;
    /**
     * 文件类型
     */
    private String type;
    /**
     * 文件uuid
     */
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
