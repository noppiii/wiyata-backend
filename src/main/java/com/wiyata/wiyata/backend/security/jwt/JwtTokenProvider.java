package com.wiyata.wiyata.backend.security.jwt;

import com.wiyata.wiyata.backend.repository.MemberRepository;
import com.wiyata.wiyata.backend.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${spring.security.jwt.secret-key}")
    private String secretKey;

    private final UserDetailsService userDetailsService;

    private final TokenRepository tokenRepository;

    private final MemberRepository memberRepository;

    @Value("${spring.security.jwt.access-valid-time}")
    private long accessTokenValidTime;

    @Value("${spring.security.jwt.refresh-valid-time}")
    private long refreshTokenValidTime;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAccessToken(String userName, List<String> roles) {
        return this.createToken(userName, roles, accessTokenValidTime);
    }

    public String createRefreshToken(String userName, List<String> roles) {
        return this.createToken(userName, roles, refreshTokenValidTime);
    }

    public String createToken(String userName, List<String> roles, Long tokenValidTime) {
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("roles", roles);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserName(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserName(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveAccessToken(HttpServletRequest request) {
        if(request.getHeader("Authorization") != null)
            return request.getHeader("Authorization").substring(7);
        return null;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
    }

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("Authorization", "bearer " + accessToken);
    }

    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(cookie);
    }

    public void setHeaderLogoutRefreshToken(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60);

        response.addCookie(cookie);
    }

    public boolean existsRefreshToken(String refreshToken) {
        return tokenRepository.existsByRefreshToken(refreshToken);
    }

    public List<String> getRoles(String userName) {
        return memberRepository.findByUserName(userName).get().getRoles();
    }
}
