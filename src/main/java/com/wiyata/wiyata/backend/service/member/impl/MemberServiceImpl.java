package com.wiyata.wiyata.backend.service.member.impl;

import com.wiyata.wiyata.backend.constant.ErrorConstant;
import com.wiyata.wiyata.backend.entity.member.Member;
import com.wiyata.wiyata.backend.entity.member.MemberInfo;
import com.wiyata.wiyata.backend.entity.member.MemberProfile;
import com.wiyata.wiyata.backend.exception.CustomException;
import com.wiyata.wiyata.backend.payload.response.member.MemberResponse;
import com.wiyata.wiyata.backend.repository.member.MemberRepository;
import com.wiyata.wiyata.backend.security.jwt.JwtTokenService;
import com.wiyata.wiyata.backend.service.global.FileService;
import com.wiyata.wiyata.backend.service.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberResponse memberResponse;
    private final JwtTokenService jwtTokenService;
    private final FileService fileService;


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

    @Override
    public ResponseEntity<Object> getMemberImg(HttpServletRequest request) throws FileNotFoundException {
        String fileName= "default_profile.png";
        Resource fileResource = fileService.loadFile(fileName);
        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(fileResource.getFile().getAbsolutePath());
        } catch (IOException e) {
            log.error("Tidak dapat load file");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getFilename() + "\"")
                .body(fileResource);
    }

    @Override
    public ResponseEntity<MemberResponse> memberEditPage(HttpServletRequest request) {
        String userName = jwtTokenService.tokenToUserName(request);
        Member member = memberRepository.findByUserName(userName).orElseThrow(() -> new CustomException(ErrorConstant.UNAUTHORIZED));
        MemberProfile memberProfile = member.getMemberProfile();
        MemberInfo memberInfo = member.getMemberInfo();

        return ResponseEntity.status(HttpStatus.OK).body(memberResponse.successGetMemberEditPage(memberProfile, memberInfo));
    }
}
