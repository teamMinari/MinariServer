package Minari.cheongForDo.global.exception;

public enum ErrorCode {
    // 사용자 관련 에러
    MEMBER_NOT_FOUND(404, "NOT_FOUND", "Member not found"),
    INVALID_MEMBER_STATUS(400, "BAD_REQUEST", "Invalid member status"),
    MEMBER_ALREADY_EXIST(409, "CONFLICT", "Member already exists"),
    MEMBER_NOT_EXIST(404, "NOT_FOUND", "Member not exist"),
    MEMBER_NOT_CORRECT(400, "BAD_REQUEST", "Member information not correct"),
    // 인증 관련 에러
    AUTHENTICATION_FAILED(401, "UNAUTHORIZED", "Authentication failed"),
    ACCESS_DENIED(403, "FORBIDDEN", "Access denied"),
    // 요청 관련 에러
    INVALID_REQUEST(400, "BAD_REQUEST", "Invalid request"),
    VALIDATION_FAILED(422, "UNPROCESSABLE_ENTITY", "Validation failed"),
    // 서버 관련 에러
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "Internal server error"),
    SERVICE_UNAVAILABLE(503, "SERVICE_UNAVAILABLE", "Service unavailable"),
    // JWT 관련 에러
    JWT_WAS_EXPIRED(403, "FORBIDDEN", "Token expired");

    private final int code;
    private final String status;
    private final String message;

    ErrorCode(int code, String status, String message) {
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
