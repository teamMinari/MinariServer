package Minari.cheongForDo.global.custom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private final CustomErrorCode errorCode;

}