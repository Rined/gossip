package com.rined.gossip.services.events;

import com.rined.gossip.model.User;
import com.rined.gossip.services.events.type.EmailSendEvent;
import com.rined.gossip.services.events.type.UserEmailSendEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import static com.rined.gossip.services.events.MailType.ACTIVATION;

@Component
@RequiredArgsConstructor
public class SendMailEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishMailActivationEvent(User user) {
        EmailSendEvent<User> event = new UserEmailSendEvent(ACTIVATION, user);
        applicationEventPublisher.publishEvent(event);
    }

}
