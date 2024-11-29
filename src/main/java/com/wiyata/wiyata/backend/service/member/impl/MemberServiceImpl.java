package com.wiyata.wiyata.backend.service.member.impl;

import com.wiyata.wiyata.backend.payload.response.member.MemberResponse;
import com.wiyata.wiyata.backend.repository.member.MemberRepository;
import com.wiyata.wiyata.backend.service.member.MemberService;
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
}
