package com.rined.justtalk.services;

import com.rined.justtalk.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface UserService extends UserDetailsService {

    boolean createUser(User user);

    void updateUser(User user, String newUserName, Map<String, String> formData);

    List<User> findAll();

    boolean activateUser(String code);

    void updateProfile(User user, String password, String email);
}
