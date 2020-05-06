package com.rined.gossip.services.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MailType {
    ACTIVATION("Activation code");

    private final String subject;
}
