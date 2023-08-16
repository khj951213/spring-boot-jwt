package com.junedev.spingbootjwtexample.controller;

import com.junedev.spingbootjwtexample.entity.Member;
import com.junedev.spingbootjwtexample.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Member> GetMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(this.memberService.findMemberById(id));
    }
    @PostMapping("")
    public ResponseEntity<Member> CreateMember(@RequestBody Member member) {
        return ResponseEntity.ok(this.memberService.saveMember(member));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> DeleteMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(this.memberService.deleteMemberById(id));
    }
}
