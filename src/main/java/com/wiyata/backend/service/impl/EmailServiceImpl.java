package com.wiyata.backend.service.impl;

import com.wiyata.backend.constant.ErrorCode;
import com.wiyata.backend.exception.BadRequestException;
import com.wiyata.backend.exception.DuplicateException;
import com.wiyata.backend.exception.NotFoundException;
import com.wiyata.backend.exception.NotMatchException;
import com.wiyata.backend.model.EmailAuth;
import com.wiyata.backend.model.SocialCode;
import com.wiyata.backend.model.User;
import com.wiyata.backend.payload.request.FindPasswordRequest;
import com.wiyata.backend.reposotiry.EmailAuthRepository;
import com.wiyata.backend.reposotiry.EmailRedisRepository;
import com.wiyata.backend.reposotiry.UserRepository;
import com.wiyata.backend.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.security.SecureRandom;
import java.util.UUID;

@Service
@EnableAsync
@Transactional
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private UserServiceImpl userService;
    private JavaMailSender javaMailSender;
    private EmailAuthRepository emailAuthRepository;
    private EmailRedisRepository emailRedisRepository;
    private UserRepository userRepository;
    private SpringTemplateEngine templateEngine;

    @Value("${spring.mail.link}")
    private String host;

    @Override
    @Async
    public void send(String email) {
        userRepository.findByEmailAndSocialCode(email, SocialCode.NORMAL).ifPresent(it -> {
            throw new DuplicateException(ErrorCode.ALREADY_REGISTER);
        });

        MimeMessage message = javaMailSender.createMimeMessage();

        if (emailRedisRepository.getToken(email).isPresent()) {
            emailRedisRepository.deleteToken(email);
        }

        String token = UUID.randomUUID().toString();
        emailRedisRepository.setToken(email, token);

        try {
            Context context = new Context();
            context.setVariable("auth_url", "%s/%s/%s".formatted(host, email, token));

            String html = templateEngine.process("email_auth_mail", context);
            message.setSubject("[WIYATA] Petunjuk untuk verifikasi email.");
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setText(html,"UTF-8", "HTML");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(message);
    }

    @Override
    @Async
    public void sendNewPassword(FindPasswordRequest findPasswordRequest) {
        String email = findPasswordRequest.getEmail();

        if (email == null || email.isEmpty()) {
            throw new BadRequestException(ErrorCode.EMPTY_EMAIL);
        }

        User user = userService.getUserByEmail(email);
        String newPassword = getRandomPassword();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            Context context = new Context();
            context.setVariable("newPassword", newPassword);
            String html = templateEngine.process("find_password_mail", context);
            message.setSubject("[WIYATA] Ini adalah informasi kata sandi sementara.");
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setText(html, "UTF-8", "HTML");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(message);
        userService.changePassword(email, newPassword);
    }

    @Override
    public boolean authEmail(String email, String authToken) {
        String token = emailRedisRepository.getToken(email)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_EMAIL_TOKEN));

        if (!token.matches(authToken)) {
            throw new NotMatchException(ErrorCode.NOT_MATCH_EMAIL_TOKEN);
        }

        return true;
    }

    @Override
    public String createEmailAuth(String email) {
        EmailAuth emailAuth = EmailAuth.builder()
                .email(email)
                .authToken(UUID.randomUUID().toString())
                .expired(false)
                .build();

        return emailAuthRepository.save(emailAuth).getAuthToken();
    }

    private String getRandomPassword() {
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        };

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        int rndAllCharactersLength = charSet.length;
        for (int i = 0; i < 8; i++) {
            sb.append(charSet[random.nextInt(rndAllCharactersLength)]);
        }

        return sb.toString();
    }
}
