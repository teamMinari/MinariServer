package Minari.cheongForDo.global.exception;

import Minari.cheongForDo.global.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<?>> handleCustomException(CustomException exception) {
        final BaseResponse<?> response = BaseResponse.of(
                false,
                exception.getErrorCode().getStatus(),
                exception.getErrorCode().getMessage(),
                null
        );

        return ResponseEntity
                .status(exception.getErrorCode().getCode())
                .body(response);
    }
}