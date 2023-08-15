package com.junedev.springbootjwt.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpReqDto {
    private String email;
    private String title;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String gender;
}
