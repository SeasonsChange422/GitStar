package com.hx.utils;

import com.hx.common.utils.ConfigUtil;
import com.hx.entity.FileNode;
import com.hx.enums.VisitBucketEnum;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author dhx
 * @date 2025/2/28 2:02
 */
public class TencentOss {
    private static String ACCESS_KEY;
    private static String SECRET_KEY;
    private static String PICTURE_BUCKET_NAME;
    private static String PICTURE_REGION;
    private static String PICTURE_BASEURL;
    private static String REPOSITORY_BUCKET_NAME;
    private static String REPOSITORY_REGION;
    private static String REPOSITORY_BASEURL;
    private static COSClient PICTURE_CLIENT;
    private static COSClient REPOSITORY_CLIENT;

    private static void init() {
        ACCESS_KEY = ConfigUtil.getValue("oss.accessKey");
        SECRET_KEY = ConfigUtil.getValue("oss.secretKey");
        PICTURE_BUCKET_NAME = ConfigUtil.getValue("oss.picture.bucketName");
        PICTURE_REGION = ConfigUtil.getValue("oss.picture.region");
        PICTURE_BASEURL = ConfigUtil.getValue("oss.picture.baseUrl");
        REPOSITORY_BUCKET_NAME = ConfigUtil.getValue("oss.repository.bucketName");
        REPOSITORY_REGION = ConfigUtil.getValue("oss.repository.region");
        REPOSITORY_BASEURL = ConfigUtil.getValue("oss.repository.baseUrl");
        COSCredentials cred = new BasicCOSCredentials(ACCESS_KEY, SECRET_KEY);
        ClientConfig pictureClientConfig = new ClientConfig(new Region(PICTURE_REGION));
        ClientConfig repositoryClientConfig = new ClientConfig(new Region(REPOSITORY_REGION));
        if(PICTURE_CLIENT == null)PICTURE_CLIENT = new COSClient(cred, pictureClientConfig);
        if(REPOSITORY_CLIENT == null)REPOSITORY_CLIENT = new COSClient(cred, repositoryClientConfig);

    }

    public static void uploadPicture(String fileName, String localUrl) {
        if (PICTURE_CLIENT == null) {
            init();
        }
        String key = PICTURE_BASEURL + fileName;
        try {
            File localFile = new File(localUrl);
            PutObjectRequest putObjectRequest = new PutObjectRequest(PICTURE_BUCKET_NAME, key, localFile);
            PICTURE_CLIENT.putObject(putObjectRequest);
            localFile.delete();
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
    }

    public static void uploadFile(String fileName, String localUrl) {
        if (REPOSITORY_CLIENT == null) {
            init();
        }
        String key = REPOSITORY_BASEURL + fileName;
        try {
            File localFile = new File(localUrl);
            PutObjectRequest putObjectRequest = new PutObjectRequest(REPOSITORY_BUCKET_NAME, key, localFile);
            REPOSITORY_CLIENT.putObject(putObjectRequest);
            localFile.delete();
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
    }
    public static byte[] getFile(VisitBucketEnum visitBucketEnum,String path) {
        byte[] bytes;
        try {
            switch (visitBucketEnum) {
                case PICTURE_BUCKET: {
                    if (PICTURE_CLIENT == null) {
                        init();
                    }
                    bytes = PICTURE_CLIENT.getObject(PICTURE_BUCKET_NAME,PICTURE_BASEURL+path).getObjectContent().readAllBytes();
                    break;
                }
                case REPOSITORY_BUCKET: {
                    if (REPOSITORY_CLIENT == null) {
                        init();
                    }
                    bytes =REPOSITORY_CLIENT.getObject(REPOSITORY_BUCKET_NAME,REPOSITORY_BASEURL+path).getObjectContent().readAllBytes();
                    break;
                }
                default: {
                    return new byte[0];
                }
            }
        } catch (Exception e){

            e.printStackTrace();
            return new byte[0];
        }
        return bytes;
    }
    public static List<FileNode> getFolder(String path) {
        if (REPOSITORY_CLIENT == null) {
            init();
        }

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(REPOSITORY_BUCKET_NAME);

        listObjectsRequest.setPrefix(path);
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setMaxKeys(1000);
        ObjectListing objectListing = null;
        List<FileNode> list = new ArrayList<>();
        do {
            try {
                objectListing = REPOSITORY_CLIENT.listObjects(listObjectsRequest);
            } catch (CosServiceException e) {
                e.printStackTrace();
                return new ArrayList<>();
            } catch (CosClientException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }

            List<String> commonPrefixes = objectListing.getCommonPrefixes();
            for (String commonPrefix : commonPrefixes) {
                list.add(new FileNode(commonPrefix,true,null));
            }
            List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
            for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {

                list.add(new FileNode(cosObjectSummary.getKey(), false,cosObjectSummary.getLastModified()));
            }
            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);
        } while (objectListing.isTruncated());
        return list;
    }

    public static byte[] createZipFromFolder(String folderPath) throws IOException {
        if (!folderPath.endsWith("/")) {
            folderPath += "/";
        }

        Path tempDir = Files.createTempDirectory("cos-download-");
        try {
            downloadFolderRecursive(folderPath, folderPath, tempDir);
            return createZipArchive(tempDir);
        } finally {
            deleteDirectoryRecursive(tempDir);
        }
    }



    private static void downloadFolderRecursive(
            String basePath,
            String currentPath,
            Path tempDir
    ) throws IOException {
        List<FileNode> nodes = getFolder(currentPath);
        for (FileNode node : nodes) {
            if (node.getFolder()) {
                String childPath = node.getPath().endsWith("/") ?
                        node.getPath() : node.getPath() + "/";
                downloadFolderRecursive(basePath, childPath, tempDir);
            } else {
                String relativePath = node.getPath().substring(basePath.length());
                Path targetPath = tempDir.resolve(relativePath.replace("/", File.separator));
                Files.createDirectories(targetPath.getParent());
                byte[] content = getFile(VisitBucketEnum.REPOSITORY_BUCKET, node.getPath());
                Files.write(targetPath, content);
            }
        }
    }

    private static byte[] createZipArchive(Path sourceDir) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            Files.walk(sourceDir)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        String zipEntryName = sourceDir.relativize(path)
                                .toString()
                                .replace(File.separator, "/");
                        try {
                            zos.putNextEntry(new ZipEntry(zipEntryName));
                            Files.copy(path, zos);
                            zos.closeEntry();
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        } catch (UncheckedIOException e) {
            throw e.getCause();
        }
        return baos.toByteArray();
    }

    private static void deleteDirectoryRecursive(Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                    throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
