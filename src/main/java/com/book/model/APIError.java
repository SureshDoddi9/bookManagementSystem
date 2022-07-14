package com.book.model;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class APIError {
    private String errorDetails;
}