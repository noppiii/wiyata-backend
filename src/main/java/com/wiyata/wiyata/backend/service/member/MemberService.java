package com.wiyata.wiyata.backend.service.member;

import com.wiyata.wiyata.backend.payload.request.member.MemberUpdateRequest;
import com.wiyata.wiyata.backend.payload.response.member.MemberResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.util.Map;

public interface MemberService {

    ResponseEntity<MemberResponse> memberValidation(String username);

    ResponseEntity<MemberResponse> checkValidNickName(String nickname);

    ResponseEntity<MemberResponse> getMemberPage(HttpServletRequest request);

    ResponseEntity<Object> getMemberImg(HttpServletRequest request) throws FileNotFoundException;

    ResponseEntity<MemberResponse> memberEditPage(HttpServletRequest request);

    ResponseEntity<MemberResponse> updateMember(MemberUpdateRequest memberUpdateRequest, HttpServletRequest request);

    ResponseEntity<MemberResponse> updateComment(Map<String, String> bio, HttpServletRequest request);
}
