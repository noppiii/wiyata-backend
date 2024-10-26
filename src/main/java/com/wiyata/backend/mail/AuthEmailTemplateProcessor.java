package com.wiyata.backend.mail;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class AuthEmailTemplateProcessor {

    public String processTemplate(String templatePath, Map<String, String> variables) throws IOException {
        ClassPathResource templateResource = new ClassPathResource(templatePath);
        String templateContent = StreamUtils.copyToString(templateResource.getInputStream(), StandardCharsets.UTF_8);

        for (Map.Entry<String, String> entry : variables.entrySet()) {
            String placeholder = "{{" + entry.getKey() + "}}";
            templateContent = templateContent.replace(placeholder, entry.getValue());
        }

        return templateContent;
    }
}
