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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailRedisRepository emailRedisRepository;

    private final Oauth2Revoke oauth2Revoke;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenUtils jwtTokenUtils;

    @Value("${profile.image.store-directory}")
    private String profileImgStoreDirectory;

    @Value("${profile.image.default-directory}")
    private String profileImgDefaultDirectory;

    @Override
    public void register(RegisterRequest registerRequest) throws IOException {
        userRepository.findByEmail(registerRequest.getEmail()).ifPresent(it -> {
            throw new DuplicateException(ErrorCode.ALREADY_REGISTER);
        });

//        if (!emailRedisRepository.existToken(registerRequest.getEmail())) {
//            throw new NotFoundException(ErrorCode.EXPIRED_EMAIL_TOKEN);
//        }

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
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String authorities = authentication.getAuthorities().stream()
                .map(a -> "ROLE_" + a.getAuthority())
                .collect(Collectors.joining(","));

        JwtToken jwtToken = jwtTokenProvider.createJwtToken(loginRequest.getEmail(), authorities);
        User member = getUserByEmail(loginRequest.getEmail());
        return LoginResponse.of(jwtToken, member);
    }

    @Override
    public Authentication validateAndGetAuthentication(String requestRefreshToken) {
        return  jwtTokenProvider.getAuthenticationByRefreshToken(requestRefreshToken);
    }

    @Override
    public JwtToken rotateToken(String requestRefreshToken) {
        Authentication authentication = validateAndGetAuthentication(requestRefreshToken);
        String userEmail = authentication.getName();

        checkLogin(userEmail);

        String currentRefreshToken = jwtTokenUtils.getRefreshToken(userEmail);

        if(isSnatch(requestRefreshToken, currentRefreshToken) == true) {
            logout(authentication.getName());
            throw new InvalidException(ErrorCode.SNATCH_TOKEN);
        }

        return jwtTokenProvider.refreshJwtToken(authentication);
    }

    @Override
    public void checkLogin(String email) {
        jwtTokenProvider.checkLogin(email);
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
    public boolean isSnatch(String requestRefreshToken, String currentRefreshToken) {
        return !currentRefreshToken.equals(requestRefreshToken);
    }

    @Override
    public String logout(String email) {
        jwtTokenUtils.deleteRefreshToken(email);
        return "Logout telah diproses.";
    }
}
