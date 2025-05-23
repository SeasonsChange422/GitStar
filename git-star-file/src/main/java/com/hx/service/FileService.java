package com.hx.service;

import com.hx.common.Result;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dhx
 * @date 2025/2/28 2:00
 */
public interface FileService {
    public ResponseEntity<Object> uploadPicture(MultipartFile file);

    public ResponseEntity<byte[]> visitPicture(String path);

    public Result uploadFile(MultipartFile file,String path);

    public Result getFolder(String path);

    public Result visitRepositoryFile(String path);

    public Result downloadRepository(String path);
}
