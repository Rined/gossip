package com.rined.gossip.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "recaptcha")
public class RecaptchaProperties {

    private String htmlSecret;

    private String apiSecret;

    private String urlTemplate;

}
