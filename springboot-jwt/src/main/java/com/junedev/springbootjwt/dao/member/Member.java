package com.junedev.springbootjwt.dao.member;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class Member {

    private Long id;
    private String email;
    private String password;

}
