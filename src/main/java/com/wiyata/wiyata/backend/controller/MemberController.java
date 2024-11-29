package com.wiyata.wiyata.backend.controller;

import com.wiyata.wiyata.backend.payload.response.member.MemberResponse;
import com.wiyata.wiyata.backend.security.jwt.JwtTokenService;
import com.wiyata.wiyata.backend.service.global.FileService;
import com.wiyata.wiyata.backend.service.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final FileService fileService;
    private final JwtTokenService jwtTokenService;

    @GetMapping("/{username}")
    public ResponseEntity<MemberResponse> checkId(@PathVariable("username") String username) {
        return memberService.memberValidation(username);
    }

    @GetMapping("/{nickname}")
    public ResponseEntity<MemberResponse> checkNickName(@PathVariable("nickname") String nickname) {
        return memberService.checkValidNickName(nickname);
    }

    @GetMapping("/my-page")
    public ResponseEntity<MemberResponse> getMemberPage(HttpServletRequest request) {
        return memberService.getMemberPage(request);
    }

    @GetMapping("/my-page/img")
    public ResponseEntity<Object> getMemberImg(HttpServletRequest request) throws FileNotFoundException {
        return memberService.getMemberImg(request);
    }

    @GetMapping("/profile/edit")
    public ResponseEntity<MemberResponse> getMemberEditPage(HttpServletRequest request) {
        return memberService.memberEditPage(request);
    }

    @PostMapping("/profile/img")
    public ResponseEntity<MemberResponse> updateProfileImg(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        return fileService.saveProfileImg(multipartFile, jwtTokenService.tokenToUserName(request));
    }
}
