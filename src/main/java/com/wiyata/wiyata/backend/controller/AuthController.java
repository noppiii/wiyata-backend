package com.wiyata.wiyata.backend.controller;

import com.wiyata.wiyata.backend.payload.request.member.MemberSaveRequest;
import com.wiyata.wiyata.backend.payload.response.MemberResponse;
import com.wiyata.wiyata.backend.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponse> signup(@RequestBody MemberSaveRequest memberSaveDto) {
        return memberService.memberSignUp(memberSaveDto);
    }
}
