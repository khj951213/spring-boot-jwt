package com.junedev.springbootjwt.service;

//import com.vmor.qms.api.entity.Member;

import com.junedev.springbootjwt.dao.member.Member;
import com.junedev.springbootjwt.jwt.JwtSecurityUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepo memberRepo;
    private final PasswordEncoder passwordEncoder;

    public Long getCurrentId() {
        return JwtSecurityUtility.getCurrentMemberId();
    }

    public Object getCurrentAuthorities() {
        return JwtSecurityUtility.getCurrentAuthorities();
    }

    public Object getCurrentCredentials() {
        return JwtSecurityUtility.getCurrentCredentials();
    }

    public Object getCurrentPrincipal() {
        return JwtSecurityUtility.getCurrentPrincipal();
    }

    public String getCurrentMemberEmail() {
        return JwtSecurityUtility.getCurrentMemberEmail();
    }

    public Member getMemberById(Long id) {
        try {
            var member = this.memberRepo.findById(id);
            return member.orElse(null);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error occurred while getting member. Id: " + id);
        }
    }

    public Member saveMember(Member member) {
        return this.memberRepo.save(member);
    }

    public Member getMemberByEmail(String email) {
        return this.memberRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("Not Found"));
    }

    public Boolean existsByEmail(String email) {
        return memberRepo.existsByEmail(email);
    }

    public Boolean deleteMemberById(Long id) {
        try {
            var member = this.memberRepo.findById(id).orElseThrow(RuntimeException::new);
            this.memberRepo.delete(member);
            return true;
        }
        catch (RuntimeException e) {
            return false;
        }
    }

    public List<Member> searchMember(String searchTerm) {
        try {
            var result = this.memberRepo.findAllBySearchTerm(searchTerm);
            return result.orElse(null);
        }
        catch (RuntimeException e) {
            throw new RuntimeException("Error occurred while searching member. Search Term: " + searchTerm);
        }
    }
}
