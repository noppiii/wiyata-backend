package com.wiyata.wiyata.backend.security.jwt;

import com.wiyata.wiyata.backend.entity.member.Member;
import com.wiyata.wiyata.backend.repository.member.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    public String tokenToNickname(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        Member member = memberRepository.findByUserName(jwtTokenProvider.getUserName(accessToken)).orElseThrow();
        return member.getMemberProfile().getNickname();
    }

    public String tokenToUserName(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        return jwtTokenProvider.getUserName(accessToken);
    }
}
