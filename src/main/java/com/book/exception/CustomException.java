package com.book.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final String message;

    public static CustomException with(ExceptionEnum exceptionEnum) {

        return CustomException.builder()
                .message(exceptionEnum.getMessage())
                .build();
    }
}
