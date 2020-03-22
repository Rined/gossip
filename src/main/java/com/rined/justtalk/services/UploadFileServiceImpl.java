package com.rined.justtalk.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class UploadFileServiceImpl implements UploadFileService {
    private final File uploadFolder;

    public UploadFileServiceImpl(@Value("${upload.path}") String uploadFolderName) {
        uploadFolder = new File(uploadFolderName);
    }

    @Override
    public File getUploadFolder() {
        return uploadFolder;
    }

    @Override
    public String getUploadFolderPath() {
        return uploadFolder.getAbsolutePath();
    }

    @Override
    public String saveFile(MultipartFile uploadFile) throws IOException {
        if (Objects.nonNull(uploadFile)) {
            String originalFilename = uploadFile.getOriginalFilename();
            Optional<String> fileNameOptional = generateFileName(originalFilename);
            if(fileNameOptional.isPresent()){
                File file = new File(uploadFolder, fileNameOptional.get());
                uploadFile.transferTo(file.toPath());
                return file.getPath();
            }
        }
        return null;
    }

    private Optional<String> generateFileName(String originalFileName) {
        if (Objects.isNull(originalFileName) || originalFileName.isEmpty()) {
            return Optional.empty();
        }
        String preventCollisionPrefix = UUID.randomUUID().toString();
        return Optional.of(String.format("%s.%s", preventCollisionPrefix, originalFileName));
    }
}
