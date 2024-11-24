package com.wiyata.wiyata.backend.controller;

import com.wiyata.wiyata.backend.payload.request.member.EmailAuthRequest;
import com.wiyata.wiyata.backend.payload.request.member.MemberSaveRequest;
import com.wiyata.wiyata.backend.payload.response.member.MemberResponse;
import com.wiyata.wiyata.backend.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponse> signup(@Valid @RequestBody MemberSaveRequest request) {
        return memberService.memberSignUp(request);
    }

    @GetMapping("/email")
    public ResponseEntity<MemberResponse> confirmEmail(@Valid @ModelAttribute EmailAuthRequest request) {
        return memberService.confirmEmail(request);
    }
}
