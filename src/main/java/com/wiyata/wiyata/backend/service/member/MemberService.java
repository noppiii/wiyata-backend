package com.wiyata.wiyata.backend.service.member;

import com.wiyata.wiyata.backend.payload.response.member.MemberResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;

public interface MemberService {

    ResponseEntity<MemberResponse> memberValidation(String username);

    ResponseEntity<MemberResponse> checkValidNickName(String nickname);

    ResponseEntity<MemberResponse> getMemberPage(HttpServletRequest request);

    ResponseEntity<Object> getMemberImg(HttpServletRequest request) throws FileNotFoundException;
}
