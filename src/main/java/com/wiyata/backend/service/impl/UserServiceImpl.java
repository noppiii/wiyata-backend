package com.wiyata.backend.service.impl;

import com.wiyata.backend.constant.ErrorCode;
import com.wiyata.backend.exception.DuplicateException;
import com.wiyata.backend.exception.InvalidException;
import com.wiyata.backend.exception.NotFoundException;
import com.wiyata.backend.model.User;
import com.wiyata.backend.payload.request.LoginRequest;
import com.wiyata.backend.payload.request.RegisterRequest;
import com.wiyata.backend.payload.response.LoginResponse;
import com.wiyata.backend.reposotiry.EmailRedisRepository;
import com.wiyata.backend.reposotiry.UserRepository;
import com.wiyata.backend.security.jwt.JwtToken;
import com.wiyata.backend.security.jwt.JwtTokenProvider;
import com.wiyata.backend.security.oauth2.Oauth2Revoke;
import com.wiyata.backend.service.UserService;
import com.wiyata.backend.util.JwtTokenUtils;
import com.wiyata.backend.util.MultipartFileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private EmailRedisRepository emailRedisRepository;

    private Oauth2Revoke oauth2Revoke;
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private JwtTokenProvider jwtTokenProvider;
    private JwtTokenUtils jwtTokenUtils;

    @Value("${profile.image.store-directory}")
    private String profileImgStoreDirectory;

    @Value("${profile.image.default-directory}")
    private String profileImgDefaultDirectory;

    @Override
    public void register(RegisterRequest registerRequest) throws IOException {
        userRepository.findByEmail(registerRequest.getEmail()).ifPresent(it -> {
            throw new DuplicateException(ErrorCode.ALREADY_REGISTER);
        });

        if (!emailRedisRepository.existToken(registerRequest.getEmail())) {
            throw new NotFoundException(ErrorCode.EXPIRED_EMAIL_TOKEN);
        }

        MultipartFile profileImg = registerRequest.getProfileImg();
        String profileImgUrl;

        if (profileImg == null || profileImg.isEmpty()) {
            profileImgUrl = "/user-profile/default-profile.png";
        } else {
            if (!MultipartFileUtils.isPermission(profileImg.getInputStream())) {
                throw new InvalidException(ErrorCode.INVALID_IMAGE_TYPE);
            }

            Path targetLocation = Paths.get(profileImgStoreDirectory).resolve(profileImg.getOriginalFilename());
            Files.copy(profileImg.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            profileImgUrl = "/store/user-profile/" + StringUtils.cleanPath(profileImg.getOriginalFilename());
        }

        User user = User.of(
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()),
                registerRequest.getNickname(),
                profileImgUrl
        );

        userRepository.save(user);
        emailRedisRepository.deleteToken(registerRequest.getEmail());
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public JwtToken rotateToken(String requestRefreshToken) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));
    }

    @Override
    public void changePassword(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));
        user.changePassword(passwordEncoder.encode(password));
    }

    @Override
    public String logout(String email) {
        return "";
    }
}
