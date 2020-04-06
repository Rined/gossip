package com.rined.gossip.services;

public interface MailSender {

    void send(String mailto, String subject, String message);

}
