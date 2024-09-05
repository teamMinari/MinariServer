package Minari.cheongForDo.domain.termary.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TermaryRequestDTO {
    private String termNm;      // 용어 이름
    private String termExplain; // 용어 설명
}
