package com.rined.justtalk.converter;

import com.rined.justtalk.model.User;
import com.rined.justtalk.model.dto.UserStatusDto;

public interface UserConverter {

    UserStatusDto toUserStatusDto(User user);

}
