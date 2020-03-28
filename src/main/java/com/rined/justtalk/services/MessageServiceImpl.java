package com.rined.justtalk.services;

import com.rined.justtalk.model.Message;
import com.rined.justtalk.model.User;
import com.rined.justtalk.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository repository;
    private final UploadFileServiceImpl uploadFileService;

    @Override
    public Page<Message> getAllMessages(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Message> getMessagesByTag(String text, Pageable pageable) {
        if (Objects.isNull(text) || text.isEmpty())
            return getAllMessages(pageable);
        return repository.findMessagesByTag(text, pageable);
    }

    @Override
    public void saveMessage(Message message, User user, MultipartFile file) {
        try {
            String fileName = uploadFileService.saveFile(file);
            message.setAuthor(user);
            message.setFilename(fileName);
            repository.save(message);
        } catch (IOException e) {
            throw new RuntimeException("Problem while save file!", e);
        }
    }

    @Override
    public void saveMessage(Message message, String text, String tag, MultipartFile file) {
        try {
            String fileName = uploadFileService.saveFile(file);
            message.setFilename(fileName);
            message.setText(text);
            message.setTag(tag);
            repository.save(message);
        } catch (IOException e) {
            throw new RuntimeException("Problem while save file!", e);
        }
    }
}