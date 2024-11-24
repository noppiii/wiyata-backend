package com.wiyata.wiyata.backend.service.member;

import com.wiyata.wiyata.backend.payload.response.member.MemberResponse;
import org.springframework.http.ResponseEntity;

public interface MemberService {

    ResponseEntity<MemberResponse> memberValidation(String username);
}
