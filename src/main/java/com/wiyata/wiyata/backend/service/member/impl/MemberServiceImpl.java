package com.wiyata.wiyata.backend.service.member.impl;

import com.wiyata.wiyata.backend.constant.ErrorConstant;
import com.wiyata.wiyata.backend.entity.member.Member;
import com.wiyata.wiyata.backend.exception.CustomException;
import com.wiyata.wiyata.backend.payload.response.member.MemberResponse;
import com.wiyata.wiyata.backend.repository.member.MemberRepository;
import com.wiyata.wiyata.backend.security.jwt.JwtTokenService;
import com.wiyata.wiyata.backend.service.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberResponse memberResponse;
    private final JwtTokenService jwtTokenService;


    @Override
    public ResponseEntity<MemberResponse> memberValidation(String username) {
        boolean isValid = memberRepository.existsByUserName(username);
        if (isValid) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(memberResponse.duplicateUsername());
        }

        return ResponseEntity.status(HttpStatus.OK).body(memberResponse.canUseUsername());
    }

    @Override
    public ResponseEntity<MemberResponse> checkValidNickName(String nickname) {
        boolean isValid = memberRepository.existsByNickName(nickname);

        if (isValid) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(memberResponse.duplicateNickName());
        }
        return ResponseEntity.status(HttpStatus.OK).body(memberResponse.canUseNickName());
    }

    @Override
    public ResponseEntity<MemberResponse> getMemberPage(HttpServletRequest request) {
        String userName  = jwtTokenService.tokenToUserName(request);
        Member member = memberRepository.findByUserName(userName).orElseThrow(() -> new CustomException(ErrorConstant.UNAUTHORIZED));
        String nickName = jwtTokenService.tokenToNickname(request);
        String bio = member.getMemberProfile().getBio();

        return ResponseEntity.status(HttpStatus.OK).body(memberResponse.successGetMemberPage(nickName, bio));
    }
}
