package com.wiyata.backend.payload.request;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class AuthenticationUser extends org.springframework.security.core.userdetails.User {

    private final com.wiyata.backend.model.User userDetails;

    public AuthenticationUser(com.wiyata.backend.model.User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);
        this.userDetails = user;
    }
}
