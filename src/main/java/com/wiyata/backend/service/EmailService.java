package com.wiyata.backend.service;

import com.wiyata.backend.payload.request.FindPasswordRequest;

public interface EmailService {

    void send(String email);

    void sendNewPassword(FindPasswordRequest findPasswordRequest);

    boolean authEmail(String email, String authToken);

    String createEmailAuth(String email);
}
