package Minari.cheongForDo.domain.member.presentation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRegisterDTO {
    private String id;
    private String password;
    private String name;
    private String email;
}

