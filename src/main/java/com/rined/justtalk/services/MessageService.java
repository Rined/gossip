package com.rined.justtalk.services;

import com.rined.justtalk.model.Message;
import com.rined.justtalk.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MessageService {

    List<Message> getAllMessages();

    List<Message> getMessagesByTag(String text);

    void saveMessage(Message message, User user, MultipartFile file);
}
