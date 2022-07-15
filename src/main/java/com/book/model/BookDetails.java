package com.book.model;

import lombok.Data;

import java.util.List;

@Data
public class BookDetails {
    private String publisher;
    private List<String> books;
}
