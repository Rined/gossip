package com.rined.gossip.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface UploadFileService {

    String saveFile(MultipartFile uploadFile) throws IOException;

    File getUploadFolder();

    String getUploadFolderPath();

}
