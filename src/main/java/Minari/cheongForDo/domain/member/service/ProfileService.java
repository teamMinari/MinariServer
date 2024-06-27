package Minari.cheongForDo.domain.member.service;

import Minari.cheongForDo.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Minari.cheongForDo.domain.member.entity.MemberEntity;

@Service
public class ProfileService {
    private final MemberRepository memberRepository;

    @Autowired
    public ProfileService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberEntity getUserProfile(Long userId) {
        return memberRepository.findById(userId).orElse(null);
    }

}
