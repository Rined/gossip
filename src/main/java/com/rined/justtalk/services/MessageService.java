package com.rined.justtalk.services;

import com.rined.justtalk.model.Message;
import com.rined.justtalk.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MessageService {

    List<Message> getAllMessages();

    void saveMessage(String text, String tag, User user, MultipartFile file);

    List<Message> getMessagesByTag(String text);
}
