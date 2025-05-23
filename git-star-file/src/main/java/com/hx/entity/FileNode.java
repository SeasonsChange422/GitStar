package com.hx.entity;

import java.util.Date;

/**
 * @author dhx
 * @date 2025/3/25 16:05
 */
public class FileNode {
    private String path;
    private Boolean isFolder;
    private Date modifyTime;

    public FileNode(String path, Boolean isFolder,Date modifyTime) {
        this.path = path;
        this.isFolder = isFolder;
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getFolder() {
        return isFolder;
    }

    public void setFolder(Boolean folder) {
        isFolder = folder;
    }
}
