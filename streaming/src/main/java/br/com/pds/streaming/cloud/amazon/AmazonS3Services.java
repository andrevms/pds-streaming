package br.com.pds.streaming.cloud.amazon;

import br.com.pds.streaming.cloud.services.CloudStorageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class AmazonS3Services implements CloudStorageServices {

    @Autowired
    private S3Client s3Client;

    @Value("${cloud.aws.cloudfront}")
    private String BASE_URL;
    @Value("${cloud.aws.bucket-name}")
    private String bucketName;

    public String uploadVideo(MultipartFile file) throws IOException {
        String key = UUID.randomUUID() + "-" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

        return  BASE_URL + key;
    }
}
