package com.junedev.spingbootjwtexample.repository;

import com.junedev.spingbootjwtexample.entity.Member;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository implements IMemberRepository {

    private final HashMap<Long, Member> memberStore = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(memberStore.get(id));
    }

    @Override
    public Member save(Member member) {
        var exMemeber = memberStore.values().stream().filter(e -> e.getEmail().equals(member)).findFirst();
        if (exMemeber.isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        sequence++;
        memberStore.put(sequence, member);
        return member;
    }

    @Override
    public boolean deleteById(Long id) {
        memberStore.remove(id);
        return true;
    }

    @Override
    public List<Member> GetMembers() {
        return new ArrayList<>(memberStore.values());
    }
}
