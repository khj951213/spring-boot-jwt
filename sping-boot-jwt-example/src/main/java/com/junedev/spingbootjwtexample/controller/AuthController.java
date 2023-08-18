package com.junedev.spingbootjwtexample.controller;

import com.junedev.spingbootjwtexample.entity.Member;
import com.junedev.spingbootjwtexample.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("SignIn")
    public ResponseEntity<Member> SignIn(@RequestParam String email, @RequestParam String password) {
        return ResponseEntity.ok(this.authService.signIn(email, password));
    }
}
