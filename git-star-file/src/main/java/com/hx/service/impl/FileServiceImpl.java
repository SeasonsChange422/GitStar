package com.hx.service.impl;

import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.common.utils.RandomUtil;
import com.hx.enums.VisitBucketEnum;
import com.hx.service.FileService;
import com.hx.utils.TencentOss;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author dhx
 * @date 2025/2/28 2:00
 */
@Service
public class FileServiceImpl implements FileService {
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    @Override
    public ResponseEntity<Object> uploadPicture(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("上传的文件不能为空");
            }
            if (file.getSize() > MAX_FILE_SIZE) {
                throw new IllegalArgumentException("文件大小超过限制");
            }

            String projectRootPath = System.getProperty("user.dir");
            String storeFolderPath = projectRootPath + File.separator + "store/tmp";
            File storeFolder = new File(storeFolderPath);
            if (!storeFolder.exists()) {
                storeFolder.mkdir();
            }
            String fileName = UUID.randomUUID() + file.getOriginalFilename();
            String filePath = storeFolderPath + File.separator + fileName;
            file.transferTo(new File(filePath));
            String id = RandomUtil.generateCode(10);
            TencentOss.uploadPicture(fileName,filePath);
            String imageUrl = "/fileApi/"+fileName;
            String altText = "";
            String imageLink = "";
            FileUploadResponse response = new FileUploadResponse(imageUrl, altText, imageLink);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
        }
    }
    @Override
    public ResponseEntity<byte[]> visitPicture(String resourceName) {
        byte[] imageBytes =TencentOss.getFile(VisitBucketEnum.PICTURE_BUCKET,resourceName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resourceName + "\"")
                .body(imageBytes);
    }

    @Override
    public Result uploadFile(MultipartFile file,String path) {
        try {
            String projectRootPath = System.getProperty("user.dir");
            String storeFolderPath = projectRootPath + File.separator + "store/tmp";
            File storeFolder = new File(storeFolderPath);
            if (!storeFolder.exists()) {
                storeFolder.mkdir();
            }
            String fileName = file.getOriginalFilename();
            String filePath = null;
            if (fileName != null) {
                filePath = storeFolderPath + File.separator+RandomUtil.generateCode(8) + fileName.substring(fileName.lastIndexOf('/')+1);
            }
            file.transferTo(new File(filePath));
            TencentOss.uploadFile(path,filePath);

            return Result.okResult();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getFolder(String path) {
        return Result.okResult(TencentOss.getFolder(path));
    }

    @Override
    public Result visitRepositoryFile(String path) {
        return Result.okResult(TencentOss.getFile(VisitBucketEnum.REPOSITORY_BUCKET,path));
    }

    @Override
    public Result downloadRepository(String path) {
        try {
            return Result.okResult(TencentOss.createZipFromFolder(path));
        } catch (IOException e) {
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    private static class FileUploadResponse {
        private int errno;
        private FileUploadData data;

        public FileUploadResponse(String url, String alt, String href) {
            this.errno = 0;
            this.data = new FileUploadData(url, alt, href);
        }

        public int getErrno() {
            return errno;
        }

        public FileUploadData getData() {
            return data;
        }
    }

    private static class FileUploadData {
        private String url;
        private String alt;
        private String href;

        public FileUploadData(String url, String alt, String href) {
            this.url = url;
            this.alt = alt;
            this.href = href;
        }

        public String getUrl() {
            return url;
        }

        public String getAlt() {
            return alt;
        }

        public String getHref() {
            return href;
        }
    }

    private static class ErrorResponse {
        private int errno;
        private String message;

        public ErrorResponse(String message) {
            this.errno = 1;
            this.message = message;
        }

        public int getErrno() {
            return errno;
        }

        public String getMessage() {
            return message;
        }
    }
}
