package com.wiyata.wiyata.backend.service.mail;

import com.wiyata.wiyata.backend.payload.request.member.MailRequest;

public interface MailService {

    MailRequest emailAuthMail(String email, String uuid);

    void sendMail(MailRequest mailRequest);

    MailRequest findPasswordMail(String tmpPassword, String userEmail);
}
