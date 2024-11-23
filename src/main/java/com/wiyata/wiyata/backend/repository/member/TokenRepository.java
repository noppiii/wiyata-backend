package com.wiyata.wiyata.backend.repository.member;

import com.wiyata.wiyata.backend.entity.member.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {

    boolean existsByRefreshToken(String token);
}
