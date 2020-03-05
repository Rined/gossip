package com.rined.justtalk.services;

import com.rined.justtalk.model.Message;
import com.rined.justtalk.model.User;
import com.rined.justtalk.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository repository;
    private final UploadFileServiceImpl uploadFileService;

    @Override
    public List<Message> getAllMessages() {
        return repository.findAll();
    }

    @Override
    public void saveMessage(String text, String tag, User user, MultipartFile file) {
        try {
            String fileName = uploadFileService.saveFile(file);
            Message message = new Message(text, tag, user, fileName);
            repository.save(message);
        } catch (IOException e) {
            throw new RuntimeException("Problem while save file!", e);
        }
    }

    @Override
    public List<Message> getMessagesByTag(String text) {
        if (Objects.isNull(text) || text.isEmpty())
            return getAllMessages();
        return repository.findMessagesByTag(text);
    }
}