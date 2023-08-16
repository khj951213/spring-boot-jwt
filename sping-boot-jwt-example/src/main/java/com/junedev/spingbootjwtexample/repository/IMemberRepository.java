package com.junedev.spingbootjwtexample.repository;

import com.junedev.spingbootjwtexample.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public interface IMemberRepository {
    Optional<Member> findById(Long id);
    Member save(Member member);
    boolean deleteById(Long id);
    List<Member> GetMembers();
}
