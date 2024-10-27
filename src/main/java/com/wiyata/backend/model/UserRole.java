package com.wiyata.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

    ADMIN("ROLE_ADMIN"),
    STAKEHOLDER("ROLE_STAKEHOLDER"),
    MEMBER("ROLE_MEMBER");

    private final String value;
}
