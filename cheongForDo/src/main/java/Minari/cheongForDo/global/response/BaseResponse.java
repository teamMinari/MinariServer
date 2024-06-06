package Minari.cheongForDo.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
public class BaseResponse<T> {
    final  boolean success;
    final String status;
    final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    final List<T> data;

    private BaseResponse(
            boolean success,
            String status,
            String message,
            List<T> data
    ) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static BaseResponse<?> of(
            boolean success,
            String status,
            String message,
            List<?> data
    ) {
        return new BaseResponse<>(
                success,
                status,
                message,
                data
        );
    }

}
