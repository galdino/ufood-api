package com.galdino.ufood.infrastructure.service.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.galdino.ufood.core.validation.storage.StorageProperties;
import com.galdino.ufood.domain.service.ImageStorageService;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3ImageStorageService implements ImageStorageService {

    private final StorageProperties storageProperties;
    private final AmazonS3 amazonS3;

    public S3ImageStorageService(StorageProperties storageProperties, AmazonS3 amazonS3) {
        this.storageProperties = storageProperties;
        this.amazonS3 = amazonS3;
    }

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
    public InputStream recover(String fileName) {
        return null;
    }

    private String getFilePath(String fileName) {
        return String.format("%s/%s", storageProperties.getS3().getDirectory(), fileName);
    }
}
