package com.book.exception;

import lombok.Getter;

public enum ExceptionEnum {

    Book_Not_Found("Given Book is not found in the database"),
    Provide_Book_Data("Please Provide Book data to create a book"),
    Book_Is_Available("The Book with same Id already available");
    @Getter
    String message;
    ExceptionEnum(String message) {
        this.message = message;
    }
}