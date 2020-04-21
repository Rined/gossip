package com.rined.gossip.services;

import com.rined.gossip.properties.GossipProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UploadFileServiceImpl implements UploadFileService {
    private final GossipProperties appProperties;

    private File uploadFolder;

    @Override
    public File getUploadFolder() {
        if (Objects.isNull(uploadFolder))
            return uploadFolder = new File(appProperties.getFilesDir());
        return uploadFolder;
    }

    @Override
    public String getUploadFolderPath() {
        return getUploadFolder().getAbsolutePath();
    }

    @Override
    public String saveFile(MultipartFile uploadFile) throws IOException {
        if (Objects.nonNull(uploadFile)) {
            String originalFilename = uploadFile.getOriginalFilename();
            Optional<String> fileNameOptional = generateFileName(originalFilename);
            if (fileNameOptional.isPresent()) {
                File file = new File(getUploadFolder(), fileNameOptional.get());
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
