package com.rined.justtalk.converter;

import com.rined.justtalk.model.User;
import com.rined.justtalk.model.dto.UserStatusDto;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {
    @Override
    public UserStatusDto toUserStatusDto(User user) {
        return new UserStatusDto(
                user.getId(),
                user.getUsername(),
                user.getRoles(),
                user.getSessions().size() > 0
        );
    }
}
