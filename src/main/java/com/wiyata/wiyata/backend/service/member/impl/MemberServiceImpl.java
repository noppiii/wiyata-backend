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
import com.wiyata.wiyata.backend.payload.response.member.MemberResponse;
import com.wiyata.wiyata.backend.repository.member.EmailAuthRepository;
import com.wiyata.wiyata.backend.repository.member.MemberRepository;
import com.wiyata.wiyata.backend.repository.member.TokenRepository;
import com.wiyata.wiyata.backend.security.jwt.JwtTokenProvider;
import com.wiyata.wiyata.backend.security.jwt.JwtTokenService;
import com.wiyata.wiyata.backend.service.mail.MailService;
import com.wiyata.wiyata.backend.service.member.MemberService;
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
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailAuthRepository emailAuthRepository;
    private final MailService mailService;
    private final MemberResponse memberResponse;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
    private final JwtTokenService jwtTokenService;

    @Override
    public ResponseEntity<MemberResponse> memberSignUp(MemberSaveRequest signUpRequest) {
        boolean canSignUp = memberRepository.existsByUserName(signUpRequest.getUserName());
        String password = passwordEncoder.encode(signUpRequest.getPassword());

        if (!canSignUp) {
            EmailAuth emailAuth = emailAuthRepository.save(EmailAuth.builder(signUpRequest).build());
            memberRepository.save(Member.builder(signUpRequest, password).build());

            MailRequest mailRequest = mailService.emailAuthMail(emailAuth.getEmail(), emailAuth.getUuid());
            mailService.sendMail(mailRequest);

            MemberResponse response = memberResponse.successMemberSignUp(signUpRequest.getNickName());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(memberResponse.failMemberSignUp());
    }

    @Override
    public ResponseEntity<MemberResponse> confirmEmail(EmailAuthRequest emailRequest) {
        EmailAuth emailAuth = emailAuthRepository.findValidAuthByEmail(emailRequest.getEmail(), emailRequest.getUuid(), LocalDateTime.now()).orElse(null);

        if (emailAuth == null) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(memberResponse.failEmailAuth());
        }

        Member member = memberRepository.findByEmail(emailRequest.getEmail())
                .orElseThrow(() -> new CustomException(ErrorConstant.NOT_FOUND));

        emailAuth.useToken();
        member.emailVerifiedSuccess();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(memberResponse.successEmailAuth());
    }

    @Override
    public ResponseEntity<MemberResponse> memberLogin(LoginRequest request, HttpServletResponse response) {
        Member member = memberRepository.findByUserName(request.getUsername()).orElse(null);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(memberResponse.invalidUsernameOrPassword());
        }

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(memberResponse.invalidUsernameOrPassword());
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getUsername(), member.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getUsername(), member.getRoles());
        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);
        tokenRepository.save(RefreshToken.builder().refreshToken(refreshToken).build());

        return ResponseEntity.status(HttpStatus.OK).body(memberResponse.successLogin(member.getMemberProfile().getNickname()));
    }

    @Override
    public ResponseEntity<MemberResponse> memberRefreshToAccess(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        if (refreshToken != null) {
            if (jwtTokenProvider.validateToken(refreshToken)) {
                String username = jwtTokenProvider.getUserName(refreshToken);
                List<String> roles = jwtTokenProvider.getRoles(username);
                String newAccessToken = jwtTokenProvider.createAccessToken(username, roles);
                jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
                this.setAuthentication(newAccessToken);

                return ResponseEntity.status(HttpStatus.CREATED).body(memberResponse.successCreateToken());
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(memberResponse.expireToken());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(memberResponse.notFoundRefreshToken());
    }

    @Override
    public ResponseEntity<MemberResponse> getMemberInfo(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);

        if (accessToken != null) {
            if (jwtTokenProvider.validateToken(accessToken)) {
                String nickname = jwtTokenService.tokenToNickname(request);
                return ResponseEntity.status(HttpStatus.OK).body(memberResponse.successLogin(nickname));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(memberResponse.expireToken());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(memberResponse.notFoundAccessToken());
    }

    public void setAuthentication(String token) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
