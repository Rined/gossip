package com.rined.gossip.services;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@RequiredArgsConstructor
public class CreateUploadDirStartupRunner implements ApplicationRunner {
    private final UploadFileService uploadFileService;

    @Override
    public void run(ApplicationArguments args) {
        File uploadFolder = uploadFileService.getUploadFolder();
        if (!uploadFolder.exists() && !uploadFolder.mkdir()) {
            throw new RuntimeException("Upload folder not found!");
        }
    }
}
