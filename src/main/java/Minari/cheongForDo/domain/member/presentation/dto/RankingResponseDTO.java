package Minari.cheongForDo.domain.member.presentation.dto;


import Minari.cheongForDo.domain.member.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RankingResponseDTO {
    private Long idx;
    private String id;
    private String title;
    private Long level;
    private Long totalExp;

    public static RankingResponseDTO of(MemberEntity member) {
        return new RankingResponseDTO(
                member.getIdx(),
                member.getId(),
                member.getTitle(),
                member.getLevel(),
                member.getTotalExp()
        );
    }
}
