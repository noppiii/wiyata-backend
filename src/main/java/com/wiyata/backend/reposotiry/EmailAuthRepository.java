package com.wiyata.backend.reposotiry;

import com.wiyata.backend.model.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long>, EmailAuthRepositoryCustom {

    Optional<EmailAuth> findTop1ByEmailAndExpiredIsTrueOrderByCreatedAtDesc(String email);

}