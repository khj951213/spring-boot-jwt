package com.junedev.springbootjwt.dto.auth;

import com.junedev.springbootjwt.dao.member.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
public class SignInReqDto {
    private String email;
    private String password;

    public SignInResDto toSignInResDTO() {
        SignInResDto res = new SignInResDto();
        res.setEmail(this.email);
        return res;
    }

    public Member toMember() {
        var member = new Member();
        member.setEmail(this.email);
        member.setPassword(this.password);
        return member;
    }
}
