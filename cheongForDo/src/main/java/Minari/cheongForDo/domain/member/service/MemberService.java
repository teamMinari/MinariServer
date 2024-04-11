package Minari.cheongForDo.domain.member.service;

import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class MemberService {
    private final MemberRepository MEMBER_REPOSITORY;

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
