package com.wiyata.wiyata.backend.repository.member;

import com.wiyata.wiyata.backend.entity.member.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {

    boolean existsByRefreshToken(String token);

    @Transactional
    @Query("SELECT r.id FROM RefreshToken r WHERE r.refreshToken = :token")
    Long findByRefreshToken(@Param("token") String token);
}
