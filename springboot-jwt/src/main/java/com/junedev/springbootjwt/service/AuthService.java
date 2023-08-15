package com.junedev.springbootjwt.service;


import com.junedev.springbootjwt.dao.member.Member;
import com.junedev.springbootjwt.dto.auth.*;
import com.junedev.springbootjwt.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final HttpServletRequest request;

    public Member signUp(SignUpReqDto req) {
        if (this.memberService.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Already Exists");
        }
        Member member = new Member();
        member.setEmail(req.getEmail());
        member.setPassword(this.passwordEncoder.encode(req.getPassword()));
        member = memberService.saveMember(member);
        return member;
    }

    public SignInResDto signIn(SignInReqDto req) {
        Member member = null;
        try {
            member = this.memberService.getMemberByEmail(req.getEmail());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword());
            Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
            if (authentication.isAuthenticated()) {
                CustomClaimDto customClaimDTO = CustomClaimDto.builder()
                        .id(member.getId())
                        .email(member.getEmail()).build();
                TokenDto tokenDto = tokenProvider.genToken(customClaimDTO, authentication);
                SignInResDto signInResDto = req.toSignInResDTO();
                signInResDto.setToken(tokenDto.getAccessToken());
                signInResDto.setId(member.getId());
                signInResDto.setEmail(member.getEmail());
                return signInResDto;
            } else {
                throw new RuntimeException("UnAuthorized");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Member updatePassword(Long memberId, String oldPassword, String newPassword) {
        Member member = null;
        try {
            var encPassword = this.passwordEncoder.encode(oldPassword);
            member = this.memberService.getMemberById(memberId);
            if (!passwordEncoder.matches(oldPassword, member.getPassword())) {
                throw new RuntimeException("Incorrect credentials");
            }
            member.setPassword(passwordEncoder.encode(newPassword));
            member = this.memberService.saveMember(member);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return member;
    }

    public Claims getClaimByToken() {
        String header = request.getHeader("Authentication");
        String prefix = header.split(" ")[0];
        String token = header.split(" ")[1];
        return this.tokenProvider.parseClaims(token);
    }
}
