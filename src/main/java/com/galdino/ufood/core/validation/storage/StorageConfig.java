package com.galdino.ufood.core.validation.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.galdino.ufood.domain.service.ImageStorageService;
import com.galdino.ufood.infrastructure.service.storage.LocalImageStorageService;
import com.galdino.ufood.infrastructure.service.storage.S3ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.galdino.ufood.core.validation.storage.StorageProperties.StorageType;

@Configuration
public class StorageConfig {

    @Autowired
    private StorageProperties storageProperties;

    @Bean
    @ConditionalOnProperty(name = "ufood.storage.type", havingValue = "s3")
    public AmazonS3 amazonS3() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(storageProperties.getS3().getAccessKeyId(),
                                                                          storageProperties.getS3().getSecretAccessKey());

        return AmazonS3ClientBuilder.standard()
                                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                                    .withRegion(storageProperties.getS3().getRegion())
                                    .build();
    }

    @Bean
    public ImageStorageService imageStorageService() {
        if (StorageType.S3.equals(storageProperties.getType())) {
            return new S3ImageStorageService();
        } else {
            return new LocalImageStorageService(storageProperties);
        }
    }

}
