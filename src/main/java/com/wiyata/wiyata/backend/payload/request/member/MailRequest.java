package com.wiyata.wiyata.backend.payload.request.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailRequest {
    private String toAddress;
    private String title;
    private String message;
    private String fromAddress;
    private String templateName;
    private Map<String, Object> variables;
}

