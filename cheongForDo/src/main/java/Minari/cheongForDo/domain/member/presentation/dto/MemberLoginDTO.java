package Minari.cheongForDo.domain.member.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginDTO {
    private String id;
    private String password;
}