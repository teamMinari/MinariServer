package Minari.cheongForDo.domain.member.presentation.dto;

import Minari.cheongForDo.domain.member.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberPointResponseDTO {
    private Long idx;
    private String id;
    private Long point;

    public static MemberPointResponseDTO of(MemberEntity member) {
        return MemberPointResponseDTO.builder()
                .idx(member.getIdx())
                .id(member.getId())
                .point(member.getPoint())
                .build();
    }
}