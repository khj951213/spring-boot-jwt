package com.junedev.springbootjwt.repository;

import com.junedev.springbootjwt.dao.member.Member;
import com.junedev.springbootjwt.dto.auth.SignInReqDto;

import java.util.List;
import java.util.Optional;

public interface IMemberRepository {

    Optional<Member> findById(int id);
    Member saveMember(SignInReqDto signInReqDto);
    List<Member> findMembers();
}
