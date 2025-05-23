package com.hx.controller;

import com.hx.common.Result;
import com.hx.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author dhx
 * @date 2025/2/21 16:07
 */
@RestController
public class FileController {
    @Autowired
    private FileService fileService;
    @PostMapping("/file/upload")
    public ResponseEntity<Object> uploadPicture(@RequestParam("wangeditor-uploaded-image") MultipartFile file){
        return fileService.uploadPicture(file);
    }
    @GetMapping("/{resourceName}")
    public ResponseEntity<byte[]> visitPicture(@PathVariable String resourceName){
        return fileService.visitPicture(resourceName);
    }
    @PostMapping("/file/uploadFile")
    public Result uploadFile(@RequestParam("file") MultipartFile file,@RequestParam String path){
        return fileService.uploadFile(file,path);
    }

    @GetMapping("/file/getFolder")
    public Result getFolder(@RequestParam("path")String path){
        return fileService.getFolder(path);
    }

    @GetMapping("/file/getFile")
    public Result visitRepositoryFile(@RequestParam("path")String path){
        return fileService.visitRepositoryFile(path);
    }

    @GetMapping("/file/downloadRepository")
    public Result downloadRepository(@RequestParam("path")String path){
        return fileService.downloadRepository(path);
    }
//    public Result createRootFolder(Long folderId){
//        return null;
//    }
//
//    public Result getFolder(String path){
//        return null;
//    }
//
//    public Result getFile(String path){
//        return null;
//    }
}
