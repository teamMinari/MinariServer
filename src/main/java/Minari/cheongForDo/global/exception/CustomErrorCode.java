package Minari.cheongForDo.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {

    // 멤버 관련 오류
    MEMBER_ALREADY_EXIST(HttpStatus.CONFLICT, "M-1", "멤버가 이미 존재합니다."),
    MEMBER_NOT_EXIST(HttpStatus.NOT_FOUND, "M-2", "멤버가 존재하지 않습니다."),
    MEMBER_NOT_CORRECT(HttpStatus.BAD_REQUEST, "M-3", "멤버 정보가 일치하지 않습니다."),
    PASSWORDS_DO_NOT_MATCH(HttpStatus.BAD_REQUEST, "M-4", "비밀번호가 일치하지 않습니다."),
    MEMBER_NOT_AUTHORITY(HttpStatus.FORBIDDEN, "M-5", "멤버의 권한이 없습니다."),

    // 용어 관련 오류
    TERM_NOT_EXIST(HttpStatus.NOT_FOUND, "T-1", "용어가 존재하지 않습니다."),

    // JWT 관련 오류
    JWT_WAS_EXPIRED(HttpStatus.FORBIDDEN, "J-1", "토큰이 만료되었습니다."),

    // 퀴즈 관련 오류
    QUESTION_NOT_EXIST(HttpStatus.NOT_FOUND, "Q-1", "질문이 존재하지 않습니다.");

    private final HttpStatus code;
    private final String status;
    private final String message;
}
