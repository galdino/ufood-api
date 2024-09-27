package com.galdino.ufood.infrastructure.service.storage;

import com.galdino.ufood.core.validation.storage.StorageProperties;
import com.galdino.ufood.domain.service.ImageStorageService;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalImageStorageService implements ImageStorageService {

    private final StorageProperties storageProperties;

    public LocalImageStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Override
    public void storage(NewImage newImage) {

        try {
            Path filePath = getFilePath(newImage.getFileName());

            FileCopyUtils.copy(newImage.getInputStream(), Files.newOutputStream(filePath));
        } catch (Exception e) {
            throw new StorageException("Image storage error.", e);
        }

    }

    @Override
    public void remove(String fileName) {

        try {
            Path filePath = getFilePath(fileName);
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new StorageException("Image remove error.", e);
        }

    }

    @Override
    public InputStream recover(String fileName) {
        try {
            Path filePath = getFilePath(fileName);
            return Files.newInputStream(filePath);
        } catch (Exception e) {
            throw new StorageException("Image retrieving error.", e);
        }
    }

    private Path getFilePath(String fileName) {
        return storageProperties.getLocal().getDirectory().resolve(Path.of(fileName));
    }
}
