package com.rined.justtalk.services;

import com.rined.justtalk.model.Message;
import com.rined.justtalk.model.User;

import java.util.List;

public interface MessageService {

    List<Message> getAllMessages();

    void saveMessage(String text, String tag, User user);

    List<Message> getMessagesByTag(String text);
}
