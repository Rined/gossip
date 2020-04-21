package com.rined.gossip.services;

import com.rined.gossip.model.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailTemplateServiceImpl implements MailTemplateService {
    private static final String PREFIX = "mail/%s";
    private static final String ACTIVATION_TEMPLATE = "activation.ftlh";

    private final Configuration freemarkerConfig;

    @Override
    public String activationTemplate(User user) {
        String username = user.getUsername();
        String activationCode = user.getActivationCode();
        try {
            Template template = freemarkerConfig.getTemplate(String.format(PREFIX, ACTIVATION_TEMPLATE));
            Map<String, String> map = new HashMap<>();
            map.put("name", username);
            map.put("activation", activationCode);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        } catch (IOException | TemplateException e) {
            return String.format(
                    "Hello, %s!\nWelcome to Gossip! " +
                            "Please, visit next link for activation: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
        }
    }

}
