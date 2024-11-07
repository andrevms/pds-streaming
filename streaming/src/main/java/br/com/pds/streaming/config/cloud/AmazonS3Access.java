package br.com.pds.streaming.config.cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AmazonS3Access {


    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public S3Client getS3Client() {
        AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client
                .builder()
                .region(Region.of("sa-east-1"))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
}
