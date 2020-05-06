package com.rined.gossip.services;

import com.rined.gossip.model.User;

public interface MailTemplateService {

    String activationTemplate(User user);

}
