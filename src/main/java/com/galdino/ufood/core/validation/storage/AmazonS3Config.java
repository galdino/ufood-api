package com.galdino.ufood.core.validation.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {

    private final StorageProperties storageProperties;

    public AmazonS3Config(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Bean
    public AmazonS3 amazonS3() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(storageProperties.getS3().getAccessKeyId(),
                                                                          storageProperties.getS3().getSecretAccessKey());

        return AmazonS3ClientBuilder.standard()
                                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                                    .withRegion(storageProperties.getS3().getRegion())
                                    .build();
    }

}
