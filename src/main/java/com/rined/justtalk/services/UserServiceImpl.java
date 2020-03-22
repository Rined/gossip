package com.rined.justtalk.services;

import com.rined.justtalk.model.Role;
import com.rined.justtalk.model.User;
import com.rined.justtalk.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final MailSender sender;

    @Override
    public boolean createUser(User user) {
        boolean exists = repository.existsByUsername(user.getUsername());
        if (!exists) {
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            user.setActivationCode(UUID.randomUUID().toString());
            repository.save(user);

            if (!StringUtils.isEmpty(user.getEmail())) {
                String message = String.format(
                        "Hello, %s!\nWelcome to Just-Talk! " +
                                "Please, visit next link for activation: http://localhost:8080/activate/%s",
                        user.getUsername(),
                        user.getActivationCode()
                );
                sender.send(user.getEmail(), "Activation code", message);
            }
        }
        return exists;
    }

    @Override
    public void updateUser(User user, String newUsername, Map<String, String> formData) {
        user.setUsername(newUsername);

        Set<Role> userRoles = user.getRoles();
        userRoles.clear();
        formData.keySet().stream()
                .filter(Role.roles()::contains)
                .forEach(role -> userRoles.add(Role.valueOf(role)));

        repository.save(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean activateUser(String code) {
        Optional<User> user = repository.findByActivationCode(code);
        if (!user.isPresent())
            return false;
        user.ifPresent((usr) -> {
            usr.setActivationCode(null);
            repository.save(usr);
        });
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userByName = repository.findByUsername(username);
        if (Objects.isNull(userByName)) {
            throw new UsernameNotFoundException("User not found!");
        }
        return userByName;
    }
}
