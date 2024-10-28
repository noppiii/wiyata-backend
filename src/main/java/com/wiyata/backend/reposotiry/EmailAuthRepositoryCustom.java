package com.wiyata.backend.reposotiry;

import com.wiyata.backend.model.EmailAuth;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailAuthRepositoryCustom {
    Optional<EmailAuth> findValidAuthByEmail(String email, String authToken, LocalDateTime now);

}
