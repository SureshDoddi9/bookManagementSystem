package com.book.service;

import com.book.exception.CustomException;
import com.book.exception.ExceptionEnum;
import com.book.model.APIResponseData;
import com.book.model.Book;
import com.book.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepo bookRepo;

    public APIResponseData<Book> getBook(int id){
        Optional<Book> book = bookRepo.findById(id);
        if(book.isPresent()){
            return APIResponseData.<Book>builder()
                    .success(true)
                    .data(book.get())
                    .build();
        }else {
            throw CustomException.with(ExceptionEnum.Book_Not_Found);
        }
    }

    public APIResponseData<List<Book>> getBooks(){
        return APIResponseData.<List<Book>>builder()
                .success(true)
                .data(bookRepo.findAll())
                .build();
    }

    public Book createBook(Book book){
           return bookRepo.save(book);
    }

    public Map<String,String> updateBook(Book book){
        Optional<Book> checkBook = bookRepo.findByName(book.getName());
        Map<String,String> map = new HashMap<>();
        if(checkBook.isPresent()){
            bookRepo.save(book);
            map.put("status","Book Updated Successfullfy");
            return map;
        }else{
            throw CustomException.with(ExceptionEnum.Book_Not_Found);
        }
    }

    public Map<String,String> deleteBook(int id){
        Optional<Book> checkBook = bookRepo.findById(id);
        Map<String,String> map = new HashMap<>();
        if(checkBook.isPresent()){
            bookRepo.delete(checkBook.get());
            map.put("status","Book Deleted Successfullfy");
            return map;
        }else{
            throw CustomException.with(ExceptionEnum.Book_Not_Found);
        }
    }
}
