package com.book.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    @Id
    private int id;
    private String name;
    private String description;
    private String author;
    private String publisher;
    private Float price;
    private int quantity;
    private int sales;
}
