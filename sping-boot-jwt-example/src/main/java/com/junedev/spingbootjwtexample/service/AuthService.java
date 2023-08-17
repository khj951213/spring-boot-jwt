package com.junedev.spingbootjwtexample.service;

import com.junedev.spingbootjwtexample.entity.Member;
import com.junedev.spingbootjwtexample.jwt.JwtTokenProvider;
import com.junedev.spingbootjwtexample.repository.IMemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final HttpServletRequest request;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final IMemberRepository memberRepository;
    public Member signIn(String email, String password) {
        try {
            var member = this.memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found."));
            var authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
            var authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            if (authentication.isAuthenticated()) {
                var token = jwtTokenProvider.generateToken(email);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
