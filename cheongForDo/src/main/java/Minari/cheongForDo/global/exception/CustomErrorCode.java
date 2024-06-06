package Minari.cheongForDo.global.exception;


public enum CustomErrorCode {
    // 사용자 관련 에러
    MEMBER_NOT_FOUND(404, "NOT_FOUND", "회원을 찾을 수 없음"),
    INVALID_MEMBER_STATUS(400, "BAD_REQUEST", "유효하지 않은 회원 상태"),
    MEMBER_ALREADY_EXIST(409, "CONFLICT", "회원이 이미 존재"),
    MEMBER_NOT_EXIST(404, "NOT_FOUND", "회원이 존재하지 않음"),
    MEMBER_NOT_CORRECT(400, "BAD_REQUEST", "회원정보가 잘못됨"),

    // 인증 관련 에러
    AUTHENTICATION_FAILED(401, "UNAUTHORIZED", "인증 실패"),
    ACCESS_DENIED(403, "FORBIDDEN", "접근이 거부됨"),

    // 요청 관련 에러
    INVALID_REQUEST(400, "BAD_REQUEST", "잘못된 요청"),
    VALIDATION_FAILED(422, "UNPROCESSABLE_ENTITY", "검증 실패"),

    // 서버 관련 에러
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "내부 서버 오류"),
    SERVICE_UNAVAILABLE(503, "SERVICE_UNAVAILABLE", "서비스를 사용할 수 없음"),

    // JWT 관련 에러
    JWT_WAS_EXPIRED(403, "FORBIDDEN", "토큰이 만료됨"),

    // 용어 관련 오류
    TERM_NOT_EXIST(404, "NOT_FOUND", "용어가 존재하지 않음");

    private final int code;
    private final String status;
    private final String message;

    CustomErrorCode(int code, String status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}