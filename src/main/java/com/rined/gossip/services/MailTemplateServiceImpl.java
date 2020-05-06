package com.rined.gossip.services;

import com.rined.gossip.model.User;
import com.rined.gossip.properties.GossipProperties;
import com.rined.gossip.properties.GossipProperties.MailTemplates;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MailTemplateServiceImpl implements MailTemplateService {
    private final Configuration freemarkerConfig;
    private final GossipProperties appProperties;

    @Override
    public String activationTemplate(User user) {
        MailTemplates mailTemplates = appProperties.getMailTemplates();
        try {
            Template template = getTemplateByName(mailTemplates.getActivation());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, Collections.singletonMap("user", user));
        } catch (IOException | TemplateException e) {
            throw new RuntimeException("Template error!", e);
        }
    }

    @Cacheable("templates")
    public Template getTemplateByName(String templateName) throws IOException {
        return freemarkerConfig.getTemplate(templateName);
    }

}
