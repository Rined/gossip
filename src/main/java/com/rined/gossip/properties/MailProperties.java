package com.rined.gossip.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {

    private String host;

    private String username;

    private String password;

    private int port;

    private String protocol;

}
