package com.junedev.main.controller;


import com.junedev.springbootjwt.dao.member.Member;
import com.junedev.springbootjwt.dto.auth.SignInReqDto;
import com.junedev.springbootjwt.dto.auth.SignInResDto;
import com.junedev.springbootjwt.dto.auth.SignUpReqDto;
import com.junedev.springbootjwt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<Member> signUp(@RequestBody SignUpReqDto signUpReqDto) {
        return ResponseEntity.ok(authService.signUp(signUpReqDto));
    }

    @PostMapping("/signIn")
    public ResponseEntity<SignInResDto> signIn(@RequestBody SignInReqDto signInReqDto) {
        return  ResponseEntity.ok(authService.signIn(signInReqDto));
    }
}