package com.rined.justtalk.services;

public interface MailSender {

    void send(String mailto, String subject, String message);

}
