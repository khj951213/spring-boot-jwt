package com.junedev.springbootjwt.dto.member;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDto {
    private Long id;
    private String email;
    private String title;
    private String firstName;
    private String lastName;
    private String phone;
    private String gender;
    private LocalDateTime lastVisited;
}
