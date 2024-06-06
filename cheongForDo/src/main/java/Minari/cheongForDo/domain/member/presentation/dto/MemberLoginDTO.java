package Minari.cheongForDo.domain.member.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberLoginDTO {

    @NotBlank
    private String id;

    @NotBlank
    private String password;

}