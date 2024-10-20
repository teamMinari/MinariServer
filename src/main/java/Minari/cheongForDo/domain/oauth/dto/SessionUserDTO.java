package Minari.cheongForDo.domain.oauth.dto;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUserDTO implements Serializable { // 직렬화 가능한 DTO

    private String email;

    public SessionUserDTO(MemberEntity member) {
        this.email = member.getEmail();
    }
}
