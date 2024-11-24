package com.wiyata.wiyata.backend.service.member.impl;

import com.wiyata.wiyata.backend.constant.ErrorConstant;
import com.wiyata.wiyata.backend.entity.member.EmailAuth;
import com.wiyata.wiyata.backend.entity.member.Member;
import com.wiyata.wiyata.backend.entity.member.RefreshToken;
import com.wiyata.wiyata.backend.exception.CustomException;
import com.wiyata.wiyata.backend.payload.request.member.EmailAuthRequest;
import com.wiyata.wiyata.backend.payload.request.member.LoginRequest;
import com.wiyata.wiyata.backend.payload.request.member.MailRequest;
import com.wiyata.wiyata.backend.payload.request.member.MemberSaveRequest;
import com.wiyata.wiyata.backend.payload.response.member.AuthResponse;
import com.wiyata.wiyata.backend.repository.member.EmailAuthRepository;
import com.wiyata.wiyata.backend.repository.member.MemberRepository;
import com.wiyata.wiyata.backend.repository.member.TokenRepository;
import com.wiyata.wiyata.backend.security.jwt.JwtTokenProvider;
import com.wiyata.wiyata.backend.security.jwt.JwtTokenService;
import com.wiyata.wiyata.backend.service.mail.MailService;
import com.wiyata.wiyata.backend.service.member.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailAuthRepository emailAuthRepository;
    private final MailService mailService;
    private final AuthResponse authResponse;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
    private final JwtTokenService jwtTokenService;

    @Override
    public ResponseEntity<AuthResponse> memberSignUp(MemberSaveRequest signUpRequest) {
        boolean canSignUp = memberRepository.existsByUserName(signUpRequest.getUserName());
        String password = passwordEncoder.encode(signUpRequest.getPassword());

        if (!canSignUp) {
            EmailAuth emailAuth = emailAuthRepository.save(EmailAuth.builder(signUpRequest).build());
            memberRepository.save(Member.builder(signUpRequest, password).build());

            MailRequest mailRequest = mailService.emailAuthMail(emailAuth.getEmail(), emailAuth.getUuid());
            mailService.sendMail(mailRequest);

            AuthResponse response = authResponse.successMemberSignUp(signUpRequest.getNickName());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(authResponse.failMemberSignUp());
    }

    @Override
    public ResponseEntity<AuthResponse> confirmEmail(EmailAuthRequest emailRequest) {
        EmailAuth emailAuth = emailAuthRepository.findValidAuthByEmail(emailRequest.getEmail(), emailRequest.getUuid(), LocalDateTime.now()).orElse(null);

        if (emailAuth == null) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(authResponse.failEmailAuth());
        }

        Member member = memberRepository.findByEmail(emailRequest.getEmail())
                .orElseThrow(() -> new CustomException(ErrorConstant.NOT_FOUND));

        emailAuth.useToken();
        member.emailVerifiedSuccess();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(authResponse.successEmailAuth());
    }

    @Override
    public ResponseEntity<AuthResponse> memberLogin(LoginRequest request, HttpServletResponse response) {
        Member member = memberRepository.findByUserName(request.getUsername()).orElse(null);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(authResponse.invalidUsernameOrPassword());
        }

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authResponse.invalidUsernameOrPassword());
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getUsername(), member.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getUsername(), member.getRoles());
        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);
        tokenRepository.save(RefreshToken.builder().refreshToken(refreshToken).build());

        return ResponseEntity.status(HttpStatus.OK).body(authResponse.successLogin(member.getMemberProfile().getNickname()));
    }

    @Override
    public ResponseEntity<AuthResponse> memberRefreshToAccess(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        if (refreshToken != null) {
            if (jwtTokenProvider.validateToken(refreshToken)) {
                String username = jwtTokenProvider.getUserName(refreshToken);
                List<String> roles = jwtTokenProvider.getRoles(username);
                String newAccessToken = jwtTokenProvider.createAccessToken(username, roles);
                jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
                this.setAuthentication(newAccessToken);

                return ResponseEntity.status(HttpStatus.CREATED).body(authResponse.successCreateToken());
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResponse.expireToken());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(authResponse.notFoundRefreshToken());
    }

    @Override
    public ResponseEntity<AuthResponse> getMemberInfo(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);

        if (accessToken != null) {
            if (jwtTokenProvider.validateToken(accessToken)) {
                String nickname = jwtTokenService.tokenToNickname(request);
                return ResponseEntity.status(HttpStatus.OK).body(authResponse.successLogin(nickname));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResponse.expireToken());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(authResponse.notFoundAccessToken());
    }

    @Override
    public ResponseEntity<AuthResponse> memberLogout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        Long id = tokenRepository.findByRefreshToken(refreshToken);

        tokenRepository.deleteById(id);
        jwtTokenProvider.setHeaderLogoutRefreshToken(response, "");

        return ResponseEntity.status(HttpStatus.OK).body(authResponse.successLogout());
    }

    public void setAuthentication(String token) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
