package com.rined.justtalk.services;

import com.rined.justtalk.model.Message;
import com.rined.justtalk.model.User;
import com.rined.justtalk.model.dto.MessageDto;
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
    public Page<MessageDto> getAllMessages(User user, Pageable pageable) {
        return repository.findAll(user, pageable);
    }

    @Override
    public Page<MessageDto> getMessages(String tag, User user, Pageable pageable) {
        if (Objects.isNull(tag) || tag.isEmpty())
            return getAllMessages(user, pageable);
        return repository.findMessagesByTag(tag, user, pageable);
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

    @Override
    public Page<MessageDto> userMessageList(Pageable pageable, User messageAuthor, User currentUser) {
        return repository.findByUser(messageAuthor, currentUser, pageable);
    }
}