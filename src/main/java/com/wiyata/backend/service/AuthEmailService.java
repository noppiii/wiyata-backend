package com.wiyata.backend.service;

import com.wiyata.backend.mail.AuthEmailTemplateProcessor;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthEmailService {

    private final JavaMailSender javaMailSender;
    private final AuthEmailTemplateProcessor authEmailTemplateProcessor;

    @Async
    @Retryable(retryFor = MessagingException.class, maxAttempts = 4, backoff = @Backoff(delay = 3000))
    public CompletableFuture<Integer> sendEmailWithRetry(String to, String otp) throws IOException {
        try {
            sendOtpByEmail(to, otp);
            return CompletableFuture.completedFuture(1);
        } catch (MessagingException e) {
            return CompletableFuture.completedFuture(handleMessagingException(e));
        } catch(UnsupportedEncodingException e) {
            return CompletableFuture.completedFuture(handleUnsupportedEncodingException(e));
        }
    }

    @Recover
    public int handleMessagingException(MessagingException e) {
        log.error("Maximum attempt reached, failed to send email");
        log.error("Error message: {}", e.getMessage());
        return -1;
    }

    @Recover
    public int handleUnsupportedEncodingException(UnsupportedEncodingException e) {
        log.error("Maximum attempt reached , failed to send email");
        log.error("Error message : {}", e.getMessage());
        return -1;
    }

    public void sendOtpByEmail(String to, String otp) throws MessagingException, UnsupportedEncodingException, IOException {
        log.info("Trying to send email to {}", to);

        String senderName = "Wiyata Software Digital";
        String from = "mnoviantoanggoro@gmail.com";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(from, senderName);
        helper.setTo(to);
        helper.setSubject("One Time Password (OTP) to verify your Email Address");

        Map<String, String> variables = new HashMap<>();
        variables.put("otp", otp);

        String htmlContent = authEmailTemplateProcessor.processTemplate("templates/otp_email_template.html", variables);
        helper.setText(htmlContent, true);

        ClassPathResource image = new ClassPathResource("static/security-removebg-preview.png");
        helper.addInline("policeOfficerImage", image);

        javaMailSender.send(message);
        log.info("Email has been sent successfully to {}", to);
    }
}
