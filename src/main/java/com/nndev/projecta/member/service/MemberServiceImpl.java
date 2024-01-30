package com.nndev.projecta.member.service;

import com.nndev.projecta.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberServiceImpl implements MemberService {
    private MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
