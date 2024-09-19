package com.galdino.ufood.infrastructure.service.storage;

import com.galdino.ufood.domain.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalImageStorageService implements ImageStorageService {

    @Value("${ufood.local.storage.directory}")
    private Path imageDirectory;

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
        return imageDirectory.resolve(Path.of(fileName));
    }
}
