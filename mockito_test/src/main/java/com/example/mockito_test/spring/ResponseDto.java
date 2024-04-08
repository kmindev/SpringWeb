package com.example.mockito_test.spring;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseDto<T> {
    private int code;
    private T data;
    private String message;

    public static <T> ResponseDto<T> ok() {
        return new ResponseDto<>(HttpStatus.OK.value(), null, null);
    }

    public static <T> ResponseDto<T> okWithData(T data) {
        return new ResponseDto<>(HttpStatus.OK.value(), data, null);
    }

    public static <T> ResponseDto<T> errorWithMessage(int code, String message) {
        return new ResponseDto<>(code, null, message);
    }
}
