package com.wiyata.wiyata.backend.service.member.impl;

import com.wiyata.wiyata.backend.constant.ErrorConstant;
import com.wiyata.wiyata.backend.entity.member.Member;
import com.wiyata.wiyata.backend.entity.member.MemberInfo;
import com.wiyata.wiyata.backend.entity.member.MemberProfile;
import com.wiyata.wiyata.backend.exception.CustomException;
import com.wiyata.wiyata.backend.payload.request.member.MemberUpdateRequest;
import com.wiyata.wiyata.backend.payload.request.member.UpdatePasswordMemberRequest;
import com.wiyata.wiyata.backend.payload.response.member.MemberResponse;
import com.wiyata.wiyata.backend.repository.MemberRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberResponse memberResponse;
    private final JwtTokenService jwtTokenService;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;


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

    @Override
    public ResponseEntity<MemberResponse> updateMember(MemberUpdateRequest memberUpdateRequest, HttpServletRequest request) {
        Long id = jwtTokenService.tokenToUserId(request);
        Member member = memberRepository.findMemberId(id).orElseThrow();

        member.getMemberProfile().setNickname(memberUpdateRequest.getNickName());
        member.getMemberProfile().setBio(memberUpdateRequest.getBio());
        member.getMemberInfo().setBrithday(memberUpdateRequest.getBirthday());
        member.getMemberInfo().setEmail(memberUpdateRequest.getEmail());
        member.getMemberInfo().setGender(memberUpdateRequest.getGender());

        return ResponseEntity.status(HttpStatus.CREATED).body(memberResponse.successEditMemberInfo(member.getMemberProfile(), member.getMemberInfo()));
    }

    @Override
    public ResponseEntity<MemberResponse> updateComment(Map<String, String> bio, HttpServletRequest request) {
        Long id  = jwtTokenService.tokenToUserId(request);
        Member member = memberRepository.findMemberId(id).orElseThrow(() -> new CustomException(ErrorConstant.UNAUTHORIZED));
        member.getMemberProfile().setBio(bio.get("bio"));

        return ResponseEntity.status(HttpStatus.CREATED).body(memberResponse.successEditMemberProfile(member.getMemberProfile()));
    }

    @Override
    public ResponseEntity<MemberResponse> updateNickName(String nickName, HttpServletRequest request) {
        Long id = jwtTokenService.tokenToUserId(request);
        Member member = memberRepository.findMemberId(id).orElseThrow(() -> new CustomException(ErrorConstant.UNAUTHORIZED));
        member.getMemberProfile().setNickname(nickName);

        return ResponseEntity.status(HttpStatus.CREATED).body(memberResponse.successEditMemberProfile(member.getMemberProfile()));
    }

    @Override
    public ResponseEntity<MemberResponse> updateMemberPwd(HttpServletRequest request, UpdatePasswordMemberRequest updatePasswordMemberRequest) {
        String userName =  jwtTokenService.tokenToUserName(request);
        Optional<Member> member = memberRepository.findByUserName(userName);
        String origin = updatePasswordMemberRequest.getOriginPassword();

        if (passwordEncoder.matches(origin, member.get().getPassword())) {
            String newPassword = passwordEncoder.encode(updatePasswordMemberRequest.getNewPassword());
            memberRepository.updateMemberPassword(newPassword, userName);

            return ResponseEntity.status(HttpStatus.CREATED).body(memberResponse.successEditMemberPassword());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(memberResponse.failEditMemberPassword());
    }
}
