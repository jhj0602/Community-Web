package com.ssafy.community.image.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        log.info("upload s3 multipartFile");
        if (!multipartFile.isEmpty()) {
            File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                    .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
            return upload(uploadFile, dirName);
        }
        return "";
    }

    // S3로 파일 업로드하기
    private String upload(File uploadFile, String dirName) {
        log.info("upload s3 uploadFile");
        String imageUrl = UUID.randomUUID()+"";
        String fileName = dirName + "/" + imageUrl;   // S3에 저장된 파일 이름
        putS3(uploadFile, fileName); // s3로 업로드
        removeNewFile(uploadFile);
        return imageUrl;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        log.info("upload s3 putS3");
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        log.info("upload s3 removeNewFile");
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        log.info("upload s3 convert");
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    public void delete(String key) {
        try {
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, "static/"+key);
            amazonS3Client.deleteObject(deleteObjectRequest);
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }
}