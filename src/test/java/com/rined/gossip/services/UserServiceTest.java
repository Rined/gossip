package com.rined.gossip.services;

import com.rined.gossip.model.Role;
import com.rined.gossip.model.User;
import com.rined.gossip.repositories.UserRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private MailSender mailSender;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void addUser() {
        val EMAIL = "test@test.test";
        val SUBJECT = "Activation code";
        val MSG_PART = "Welcome to Just-Talk!";

        User user = new User();
        user.setEmail(EMAIL);
        boolean isUserExists = userService.createUser(user);
        assertAll(
                () -> assertFalse(isUserExists),
                () -> assertThat(user.getActivationCode()).isNotBlank(),
                () -> assertThat(user.getRoles()).containsOnly(Role.USER),
                () -> verify(userRepo, times(1)).save(user),
                () -> verify(mailSender, times(1))
                        .send(eq(EMAIL), eq(SUBJECT), contains(MSG_PART))
        );
    }

    @Test
    void addUserFailTest() {
        val USER_NAME = "Test";

        User user = new User();
        user.setUsername(USER_NAME);
        doReturn(true)
                .when(userRepo)
                .existsByUsername(USER_NAME);

        boolean isUserExists = userService.createUser(user);
        assertAll(
                () -> assertTrue(isUserExists),
                () -> verify(userRepo, times(0)).save(any(User.class)),
                () -> verify(mailSender, times(0))
                        .send(anyString(), anyString(), anyString())
        );

    }

    @Test
    void activateUser() {
        val ACTIVATION_CODE = "activation_code";
        val user = new User();

        user.setActivationCode(ACTIVATION_CODE);

        doReturn(Optional.of(user))
                .when(userRepo)
                .findByActivationCode(ACTIVATION_CODE);

        boolean isUserActivate = userService.activateUser(ACTIVATION_CODE);

        assertAll(
                () -> assertTrue(isUserActivate),
                () -> assertNull(user.getActivationCode()),
                () -> verify(userRepo, times(1)).save(user)
        );
    }

    @Test
    void activateUserFailTest() {
        boolean isUserActivate = userService.activateUser("activation_code");
        assertAll(
                () -> assertFalse(isUserActivate),
                () -> verify(userRepo, times(0)).save(any(User.class))
        );
    }
}