package com.wiyata.wiyata.backend.repository;

import com.wiyata.wiyata.backend.entity.member.EmailAuth;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailAuthCustomRepository {

    @Query("SELECT ea FROM EmailAuth ea WHERE ea.email = :email AND ea.uuid = :uuid AND ea.expireDate > :currentTime")
    Optional<EmailAuth> findValidAuthByEmail(@Param("email") String email, @Param("uuid") String uuid, @Param("currentTime") LocalDateTime currentTime);
}

