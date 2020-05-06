package com.rined.gossip.services.events.type;


import com.rined.gossip.services.events.MailType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EmailSendEvent<T> {
    private final MailType mailType;
    private final T payload;
}
