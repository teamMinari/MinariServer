package Minari.cheongForDo.domain.member.presentation.dto;

import Minari.cheongForDo.domain.member.authority.MemberAccountType;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberResponseDTO {

    private Long idx;
    private String id;
    private String email;
    private Long point;
    private Long exp;
    private MemberAccountType authority;
    private String title;
    private Long level;
    private Long checkLevel; // 보상 해금 단계
    private Long totalExp;

    public static MemberResponseDTO of (MemberEntity member) {
        return MemberResponseDTO.builder()
                .idx(member.getIdx())
                .id(member.getId())
                .email(member.getEmail())
                .point(member.getPoint())
                .exp(member.getExp())
                .authority(member.getAuthority())
                .title(member.getTitle())
                .level(member.getLevel())
                .checkLevel(member.getCheckLevel())
                .build();
    }
}
