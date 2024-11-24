package com.wiyata.wiyata.backend.service.member.impl;

import com.wiyata.wiyata.backend.constant.ErrorConstant;
import com.wiyata.wiyata.backend.entity.member.EmailAuth;
import com.wiyata.wiyata.backend.entity.member.Member;
import com.wiyata.wiyata.backend.exception.CustomException;
import com.wiyata.wiyata.backend.payload.request.member.EmailAuthRequest;
import com.wiyata.wiyata.backend.payload.request.member.MailRequest;
import com.wiyata.wiyata.backend.payload.request.member.MemberSaveRequest;
import com.wiyata.wiyata.backend.payload.response.member.MemberResponse;
import com.wiyata.wiyata.backend.repository.member.EmailAuthRepository;
import com.wiyata.wiyata.backend.repository.member.MemberRepository;
import com.wiyata.wiyata.backend.service.mail.MailService;
import com.wiyata.wiyata.backend.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
            throw new CustomException(ErrorConstant.METHOD_NOT_ALLOWED);
        }

        Member member = memberRepository.findByEmail(emailRequest.getEmail())
                .orElseThrow(() -> new CustomException(ErrorConstant.NOT_FOUND));

        emailAuth.useToken();
        member.emailVerifiedSuccess();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(memberResponse.successEmailAuth());
    }
}
