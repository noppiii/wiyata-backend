package com.wiyata.backend.service;

import com.wiyata.backend.exception.ResourceNotFoundException;
import com.wiyata.backend.model.user.User;
import com.wiyata.backend.payload.response.GeneralAPIResponse;
import com.wiyata.backend.payload.response.RefreshTokenResponse;
import com.wiyata.backend.payload.response.RegisterVerifyResponse;
import com.wiyata.backend.repository.UserRepository;
import com.wiyata.backend.security.JwtHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    public RegisterVerifyResponse generateJwtToken(User user) {
        String myAccessToken = jwtHelper.generateAccessToken(user);
        String myRefreshToken = jwtHelper.generateRefreshToken(user);

        return RegisterVerifyResponse.builder()
                .accessToken(myAccessToken)
                .refreshToken(myRefreshToken)
                .firstName(user.getName().getFirstName())
                .lastName(user.getName().getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .isVerified(user.getIsVerified())
                .build();
    }

    public ResponseEntity<?> generateAccessTokenFromRefreshToken(String refreshToken) {
        if (refreshToken != null) {
            try {
                String username = jwtHelper.extractUsername(refreshToken);
                if (username.startsWith("#refresh")) {
                    String finalUsername = username.substring(8);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(finalUsername);
                    User user = userRepository.findByEmail(finalUsername).orElseThrow(
                            () -> new ResourceNotFoundException("User not found with email " + finalUsername)
                    );
                    if (jwtHelper.isRefreshTokenValid(refreshToken, userDetails)) {
                        String accessToken = jwtHelper.generateAccessToken(userDetails);
                        return new ResponseEntity<>(RefreshTokenResponse.builder()
                                .accessToken(accessToken)
                                .firstName(user.getName().getFirstName())
                                .lastName(user.getName().getLastName())
                                .email(user.getEmail())
                                .role(user.getRole())
                                .build(), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(GeneralAPIResponse.builder().message("Refresh token is expired").build(), HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>(GeneralAPIResponse.builder().message("Invalid refresh token").build(), HttpStatus.BAD_REQUEST);
                }
            } catch (IllegalArgumentException | MalformedJwtException e) {
                return new ResponseEntity<>(GeneralAPIResponse.builder().message("Invalid refresh token").build(), HttpStatus.BAD_REQUEST);
            } catch (ResourceNotFoundException e) {
                return new ResponseEntity<>(GeneralAPIResponse.builder().message("User not found").build(), HttpStatus.NOT_FOUND);
            } catch (ExpiredJwtException e) {
                return new ResponseEntity<>(GeneralAPIResponse.builder().message("Refresh token is expired").build(), HttpStatus.BAD_REQUEST);
            }

        } else {
            return new ResponseEntity<>(GeneralAPIResponse.builder().message("Refresh token is null").build(), HttpStatus.BAD_REQUEST);
        }

    }

}



