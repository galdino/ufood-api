package com.galdino.ufood.infrastructure.service.storage;

import com.galdino.ufood.domain.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

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

    private Path getFilePath(String fileName) {
        return imageDirectory.resolve(Path.of(fileName));
    }
}
