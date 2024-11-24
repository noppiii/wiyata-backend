package com.wiyata.wiyata.backend.repository.member;

import com.wiyata.wiyata.backend.entity.member.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long>, EmailAuthCustomRepository {
}
