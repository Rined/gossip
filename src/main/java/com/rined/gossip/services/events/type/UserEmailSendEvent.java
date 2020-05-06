package com.rined.gossip.services.events.type;

import com.rined.gossip.model.User;
import com.rined.gossip.services.events.MailType;

public class UserEmailSendEvent extends EmailSendEvent<User> {
    public UserEmailSendEvent(MailType mailType, User payload) {
        super(mailType, payload);
    }
}
