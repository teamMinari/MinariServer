package Minari.cheongForDo.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final CustomErrorCode ERROR_CODE;

    public CustomException(CustomErrorCode errorCode) {
        super(errorCode.getMessage());
        this.ERROR_CODE = errorCode;
    }

    public CustomErrorCode getErrorCode() {
        return ERROR_CODE;
    }
}