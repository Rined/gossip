package com.rined.justtalk.services;

import com.rined.justtalk.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    boolean createUser(User user);

}
