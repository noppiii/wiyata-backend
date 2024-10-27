package com.wiyata.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialCode {

    GOOGLE("google"),
    NORMAL("normal");

    private final String type;
}
