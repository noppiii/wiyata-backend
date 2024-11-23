package com.wiyata.wiyata.backend.service.mail.impl;

import com.wiyata.wiyata.backend.payload.request.member.MailRequest;
import com.wiyata.wiyata.backend.service.mail.MailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Service
@Transactional
@EnableAsync
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public MailRequest emailAuthMail(String email, String uuid) {
        String title = "Panduan Verifikasi Email untuk Pendaftaran Wiyata";
        String authUrl = "http://localhost:8080/auth/email?email=" + email + "&uuid=" + uuid;
        String fromAddress = "mnoviantoanggoro@gmail.com";

        return MailRequest.builder()
                .toAddress(email)
                .title(title)
                .message(null)
                .fromAddress(fromAddress)
                .templateName("verification_mail")
                .variables(Map.of("auth_url", authUrl))
                .build();
    }

    @Override
    public void sendMail(MailRequest mailRequest) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(mailRequest.getToAddress());
            helper.setSubject(mailRequest.getTitle());
            helper.setFrom(mailRequest.getFromAddress());
            helper.setReplyTo(mailRequest.getFromAddress());

            if (mailRequest.getTemplateName() != null) {
                Context context = new Context();
                if (mailRequest.getVariables() != null) {
                    context.setVariables(mailRequest.getVariables());
                }
                String htmlContent = templateEngine.process(mailRequest.getTemplateName(), context);
                helper.setText(htmlContent, true);
            } else {
                helper.setText(mailRequest.getMessage(), false);
            }

            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            log.error("Gagal mengirim email ke: {}", mailRequest.getToAddress(), e);
            throw new RuntimeException("Gagal mengirim email", e);
        }
    }
}
