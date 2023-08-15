package com.junedev.springbootjwt.repository;

import com.junedev.springbootjwt.dao.member.Member;
import com.junedev.springbootjwt.dto.auth.SignInReqDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MemberRepositoryImpl implements  IMemberRepository {

    private static final HashMap<Member, Long> memberRepo = new HashMap<Member, Long>();
    private static Long memberId = 0L;


    @Override
    public Optional<Member> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Member saveMember(SignInReqDto signInReqDto) {
        var member = signInReqDto.toMember();
        memberRepo.put(member, memberId++);
        return member;
    }

    @Override
    public List<Member> findMembers() {
        return new ArrayList<>(memberRepo);
    }
}
