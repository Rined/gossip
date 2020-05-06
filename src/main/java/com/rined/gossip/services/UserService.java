package com.rined.gossip.services;

import com.rined.gossip.model.User;
import com.rined.gossip.model.dto.UserStatusDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

public interface UserService extends UserDetailsService {

    boolean createUser(User user);

    void updateUser(User user, String newUserName, Map<String, String> formData);

    Page<UserStatusDto> findAll(Pageable pageable);

    boolean activateUser(String code);

    void updateProfile(User user, String password, String email);

    void subscribe(User currentUser, User user);

    void unsubscribe(User currentUser, User user);

    void kickUser(User user);

}
