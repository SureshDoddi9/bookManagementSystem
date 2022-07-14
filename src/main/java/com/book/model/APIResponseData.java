package com.book.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class APIResponseData<T> {
    private boolean success;
    private T data;
    private List<APIError> errors;
}