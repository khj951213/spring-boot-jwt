package com.junedev.springbootjwt.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CustomClaimDto {
    private Long id;
    private String title;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private LocalDateTime lastVisited;
}
