package com.junedev.springbootjwt.dto.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdatePasswordDto {
    private String oldPassword;
    private String newPassword;
}
