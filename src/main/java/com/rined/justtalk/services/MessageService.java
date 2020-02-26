package com.rined.justtalk.services;

import com.rined.justtalk.model.Message;

import java.util.List;

public interface MessageService {

    List<Message> getAllMessages();

    void saveMessage(String text, String tag);

    List<Message> getMessagesByTag(String text);
}
