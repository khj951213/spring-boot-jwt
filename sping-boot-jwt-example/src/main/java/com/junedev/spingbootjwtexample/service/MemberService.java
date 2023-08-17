package com.junedev.spingbootjwtexample.service;

import com.junedev.spingbootjwtexample.entity.Member;
import com.junedev.spingbootjwtexample.jwt.JwtTokenProvider;
import com.junedev.spingbootjwtexample.repository.IMemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final IMemberRepository memberRepository;
    public Member findMemberById(Long id) {
        return this.memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }
    public Member saveMember(Member member) {
        var res = this.memberRepository.save(member);
        return res;
    }

    public boolean deleteMemberById(Long id) {
        return this.memberRepository.deleteById(id);
    }

    public List<Member> GetMembers() {
        return this.memberRepository.GetMembers();
    }

}
