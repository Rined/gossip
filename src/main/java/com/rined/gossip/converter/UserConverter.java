package com.rined.gossip.converter;

import com.rined.gossip.model.User;
import com.rined.gossip.model.dto.UserStatusDto;

public interface UserConverter {

    UserStatusDto toUserStatusDto(User user);

}
