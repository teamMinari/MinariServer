package Minari.cheongForDo.domain.user.service;

import Minari.cheongForDo.domain.user.entity.Member;
import Minari.cheongForDo.domain.user.repository.MemberRepository;

public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member){
        validateDupliacteUser(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDupliacteUser(Member member){
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

}
