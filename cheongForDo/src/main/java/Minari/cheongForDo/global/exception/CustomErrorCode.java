package Minari.cheongForDo.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {

    // 멤버 관련 오류
    MEMBER_ALREADY_EXIST(HttpStatus.CONFLICT, "M-1", "멤버가 이미 존재한다"),
    MEMBER_NOT_EXIST(HttpStatus.NOT_FOUND, "M-2", "멤버가 존재하지 않는다"),
    MEMBER_NOT_CORRECT(HttpStatus.BAD_REQUEST, "M-3", "멤버 정보가 일치하지 않다"),

    // 용어 관련 오류
    TERM_NOT_EXIST(HttpStatus.NOT_FOUND, "T-1", "용어가 존재하지 않음"),

    // JWT 관련 오류
    JWT_WAS_EXPIRED(HttpStatus.FORBIDDEN, "J-1", "토큰이 만료되었다"),

    // 퀴즈 관련 오류
    QUESTION_NOT_EXIST(HttpStatus.NOT_FOUND, "Q-1", "질문이 존재하지 않음");

    private final HttpStatus code;
    private final String status;
    private final String message;
}
