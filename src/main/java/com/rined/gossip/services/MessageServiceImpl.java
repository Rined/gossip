package com.rined.gossip.services;

import com.rined.gossip.model.Message;
import com.rined.gossip.model.User;
import com.rined.gossip.model.dto.MessageDto;
import com.rined.gossip.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

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
        return repository.findMessageDtoBy(messageAuthor, currentUser, pageable);
    }

    @Override
    public void like(Message message, User currentUser) {
        Set<User> likes = message.getLikes();
        if (likes.contains(currentUser)) {
            likes.remove(currentUser);
        } else {
            likes.add(currentUser);
        }
    }
}