package com.rined.justtalk.services;

import com.rined.justtalk.model.Message;
import com.rined.justtalk.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MessageService {

    Page<Message> getAllMessages(Pageable pageable);

    Page<Message> getMessagesByTag(String text, Pageable pageable);

    void saveMessage(Message message, User user, MultipartFile file);

    void saveMessage(Message message, String text, String tag, MultipartFile file);
}
