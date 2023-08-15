package com.junedev.springbootjwt.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SignInResDto {
    private Long id;
    private String title;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private LocalDateTime lastVisited;
    private String token;
}
