package Minari.cheongForDo.domain.member.service;

import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.member.repository.MemberRepository;
import org.aspectj.weaver.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class MemberService {
    private final MemberRepository MEMBER_REPOSITORY;

    private Map<String, Member> members = new HashMap<>();


    @Autowired
    public MemberService(MemberRepository MEMBER_REPOSITORY) {
        this.MEMBER_REPOSITORY = MEMBER_REPOSITORY;

    }

    public MemberEntity registerWithGoogle(MemberEntity member) {
        return MEMBER_REPOSITORY.save(member);
    }

    public MemberEntity findById(Long id) {
        return MEMBER_REPOSITORY.findById(id).orElse(null);
    }

}
