package com.rined.gossip.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "gossip.app")
public class GossipProperties {

    private String filesDir;

    private MailTemplates mailTemplates;

    @Data
    public static class MailTemplates {
        private String activation;
    }
}
