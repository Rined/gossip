package com.rined.justtalk.services;

import com.rined.justtalk.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

public interface UserService extends UserDetailsService {

    boolean createUser(User user);

    void updateUser(User user, String newUserName, Map<String, String> formData);

}
