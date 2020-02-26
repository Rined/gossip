package com.rined.justtalk.services;

import com.rined.justtalk.model.Role;
import com.rined.justtalk.model.User;
import com.rined.justtalk.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public boolean createUser(User user) {
        boolean exists = repository.existsByUsername(user.getUsername());
        if(!exists){
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            repository.save(user);
        }
        return !exists;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userByName = repository.findByUsername(username);
        if(Objects.isNull(userByName)){
            throw new UsernameNotFoundException("User not found!");
        }
        return userByName;
    }
}
