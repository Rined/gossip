package com.rined.justtalk.services;

import com.rined.justtalk.model.Message;
import com.rined.justtalk.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository repository;

    @Override
    public List<Message> getAllMessages() {
        return repository.findAll();
    }

    @Override
    public void saveMessage(String text, String tag) {
        Message message = new Message(text, tag);
        repository.save(message);
    }

    @Override
    public List<Message> getMessagesByTag(String text) {
        if (Objects.isNull(text) || text.isEmpty())
            return getAllMessages();
        return repository.findMessagesByTag(text);
    }
}