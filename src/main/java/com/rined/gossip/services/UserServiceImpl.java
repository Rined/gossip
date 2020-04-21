package com.rined.gossip.services;

import com.rined.gossip.converter.UserConverter;
import com.rined.gossip.model.Role;
import com.rined.gossip.model.User;
import com.rined.gossip.model.dto.UserStatusDto;
import com.rined.gossip.repositories.UserRepository;
import com.rined.gossip.services.events.SendMailEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserConverter userConverter;
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final FindByIndexNameSessionRepository<? extends Session> sessionRepository;
    private final SendMailEventPublisher mailEventPublisher;

    @Override
    public boolean createUser(User user) {
        boolean exists = repository.existsByUsername(user.getUsername());
        if (!exists) {
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            user.setActivationCode(UUID.randomUUID().toString());
            user.setPassword(encoder.encode(user.getPassword()));
            repository.save(user);
            mailEventPublisher.publishMailActivationEvent(user);
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
    public Page<UserStatusDto> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(userConverter::toUserStatusDto);
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
    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();

        boolean isEmailChanged = (Objects.nonNull(email) && !email.equals(userEmail))
                || (Objects.nonNull(userEmail) && !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);

            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!StringUtils.isEmpty(password))
            user.setPassword(encoder.encode(password));

        repository.save(user);

        if (isEmailChanged)
            mailEventPublisher.publishMailActivationEvent(user);
    }

    @Override
    public void subscribe(User currentUser, User user) {
        user.getSubscribers().add(currentUser);
        repository.save(user);
    }

    @Override
    public void unsubscribe(User currentUser, User user) {
        user.getSubscribers().remove(currentUser);
        repository.save(user);
    }

    @Override
    public void kickUser(User user) {
        sessionRepository.findByPrincipalName(user.getUsername()).keySet().forEach(sessionRepository::deleteById);
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
