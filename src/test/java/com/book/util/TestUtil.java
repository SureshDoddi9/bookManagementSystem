package com.book.util;

import com.book.model.Book;

public class TestUtil {

    public static Book getBook(){
        return Book.builder()
                .id(101)
                .name("myBook")
                .author("Suresh")
                .description("Book About Suresh Biography")
                .price(15000F)
                .quantity(15)
                .publisher("Rk")
                .sales(20).build();
    }

    public static Book getBook2(){
        return Book.builder()
                .id(102)
                .name("myBook")
                .author("Surya")
                .description("Book About Surya Biography")
                .price(15000F)
                .quantity(25)
                .publisher("Rk")
                .sales(10).build();
    }

    public static Book getBook3(){
        return Book.builder()
                .id(103)
                .name("Java")
                .author("Antony")
                .description("Book About Java")
                .price(15000F)
                .quantity(35)
                .publisher("Rk")
                .sales(20).build();
    }
}
