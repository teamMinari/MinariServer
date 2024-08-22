package Minari.cheongForDo.domain.member.presentation.dto;


import Minari.cheongForDo.domain.member.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberExpResponseDTO {
    private Long idx;
    private String id;
    private Long totalExp;
    private Long level;

    public static MemberExpResponseDTO of(MemberEntity member) {
        return MemberExpResponseDTO.builder()
                .idx(member.getIdx())
                .id(member.getId())
                .totalExp(member.getTotalExp())
                .level(member.getLevel())
                .build();
    }
}
