package com.junedev.springbootjwt.dto.member;

import com.junedev.springbootjwt.dao.member.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateDto {
    private String title;
    private String firstName;
    private String lastName;
    private String phone;
    private String gender;

    public Member toMember() {
        Member member = new Member();
        member.setTitle(this.title);
        member.setFirstName(this.firstName);
        member.setLastName(this.lastName);
        member.setPhone(this.phone);
        member.setGender(this.gender);
        return member;
    }
}
