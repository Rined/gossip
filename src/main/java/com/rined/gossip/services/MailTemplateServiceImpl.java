package com.rined.gossip.services;

import com.rined.gossip.model.User;
import com.rined.gossip.properties.GossipProperties;
import com.rined.gossip.properties.GossipProperties.MailTemplates;
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
    private final Configuration freemarkerConfig;
    private final GossipProperties appProperties;

    @Override
    public String activationTemplate(User user) {
        MailTemplates mailTemplates = appProperties.getMailTemplates();
        String username = user.getUsername();
        String activationCode = user.getActivationCode();
        try {
            Template template = freemarkerConfig.getTemplate(mailTemplates.getActivation());
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
