package com.wiyata.backend.reposotiry;

import com.wiyata.backend.model.SocialCode;
import com.wiyata.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndSocialCode(String email, SocialCode socialCode);
}
