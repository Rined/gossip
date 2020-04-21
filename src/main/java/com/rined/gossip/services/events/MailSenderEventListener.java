package com.rined.gossip.services.events;

import com.rined.gossip.model.User;
import com.rined.gossip.services.MailSender;
import com.rined.gossip.services.MailTemplateService;
import com.rined.gossip.services.events.type.EmailSendEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailSenderEventListener {
    private final MailSender sender;
    private final MailTemplateService mailTemplateService;

    @Async  // see @EnableAsync at AppConfig.class
    @EventListener(condition = "#emailEvent.mailType == T(com.rined.gossip.services.events.MailType).ACTIVATION")
    public void handleActivateMail(EmailSendEvent<User> emailEvent) {
        MailType mailType = emailEvent.getMailType();
        User payload = emailEvent.getPayload();
        sender.sendHtml(payload.getEmail(), mailType.getSubject(), mailTemplateService.activationTemplate(payload));
    }

}
