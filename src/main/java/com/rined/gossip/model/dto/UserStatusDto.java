package com.rined.gossip.model.dto;

import com.rined.gossip.model.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public class UserStatusDto {
    private final long id;
    private final String username;
    private final Set<Role> roles;
    private final boolean active;
}
