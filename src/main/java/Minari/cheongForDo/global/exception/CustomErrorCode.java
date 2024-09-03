package Minari.cheongForDo.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    // 서버 오류
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S-1", "서버에서 오류가 발생했습니다."),

    // 멤버 관련 오류
    MEMBER_ALREADY_EXIST(HttpStatus.CONFLICT, "M-1", "멤버가 이미 존재합니다."),
    MEMBER_NOT_EXIST(HttpStatus.NOT_FOUND, "M-2", "멤버가 존재하지 않습니다."),
    MEMBER_NOT_CORRECT(HttpStatus.BAD_REQUEST, "M-3", "멤버 정보가 일치하지 않습니다."),
    PASSWORDS_DO_NOT_MATCH(HttpStatus.BAD_REQUEST, "M-4", "비밀번호가 일치하지 않습니다."),
    MEMBER_NOT_AUTHORITY(HttpStatus.FORBIDDEN, "M-5", "멤버의 권한이 없습니다."),

    // 뉴스 관련 오류
    INVALID_NEWS_CATEGORY(HttpStatus.BAD_REQUEST, "NEWS-1", "카테고리가 잘못 되었습니다."),

    // 용어 관련 오류
    TERM_NOT_EXIST(HttpStatus.NOT_FOUND, "T-1", "용어가 존재하지 않습니다."),

    // JWT 관련 오류
    JWT_WAS_EXPIRED(HttpStatus.FORBIDDEN, "J-1", "토큰이 만료되었습니다."),

    // 퀴즈 관련 오류
    QUESTION_NOT_EXIST(HttpStatus.NOT_FOUND, "Q-1", "질문이 존재하지 않습니다."),

    // 튜토리얼 관련 오류
    GRAPESEED_NOT_EXIST(HttpStatus.NOT_FOUND, "G-1", "포도씨가 존재하지 않습니다."),
    GRAPE_NOT_EXIST(HttpStatus.NOT_FOUND, "G-2", "포도알이 존재하지 않습니다."),
    GRAPES_NOT_EXIST(HttpStatus.NOT_FOUND, "G-3", "포도송이가 존재하지 않습니다."),

    // 학습 관련 오류
    LEARN_ALREADY_EXIST(HttpStatus.CONFLICT, "L-1", "학습이 이미 완료되었습니다.");

    private final HttpStatus code;
    private final String status;
    private final String message;
}
