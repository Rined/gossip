package com.rined.gossip.services;

import com.rined.gossip.model.Message;
import com.rined.gossip.model.User;
import com.rined.gossip.model.dto.MessageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface MessageService {

    Page<MessageDto> getAllMessages(User user, Pageable pageable);

    Page<MessageDto> getMessages(String tag, User user, Pageable pageable);

    void saveMessage(Message message, User user, MultipartFile file);

    void saveMessage(Message message, String text, String tag, MultipartFile file);

    Page<MessageDto> userMessageList(Pageable pageable, User messageAuthor, User currentUser);
}
