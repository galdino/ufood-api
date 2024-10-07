package com.galdino.ufood.infrastructure.service.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.galdino.ufood.core.validation.storage.StorageProperties;
import com.galdino.ufood.domain.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;

public class S3ImageStorageService implements ImageStorageService {

    @Autowired
    private StorageProperties storageProperties;
    @Autowired
    private AmazonS3 amazonS3;

    @Override
    public void storage(NewImage newImage) {

        try {
            String filePath = getFilePath(newImage.getFileName());

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(newImage.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(storageProperties.getS3().getBucket(), filePath,
                                                                     newImage.getInputStream(), objectMetadata)
                                                                    .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Image Amazon S3 storage error.", e);
        }

    }

    @Override
    public void remove(String fileName) {
        try {
            String filePath = getFilePath(fileName);

            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(), filePath);

            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Image Amazon S3 remove error.", e);
        }
    }

    @Override
    public RecoveredImage recover(String fileName) {
        String filePath = getFilePath(fileName);

        URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), filePath);

        return RecoveredImage.builder().url(url.toString()).build();
    }

    private String getFilePath(String fileName) {
        return String.format("%s/%s", storageProperties.getS3().getDirectory(), fileName);
    }
}
