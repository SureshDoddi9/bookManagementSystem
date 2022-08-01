package com.book.service;

import com.book.exception.CustomException;
import com.book.exception.ExceptionEnum;
import com.book.model.APIResponseData;
import com.book.model.Book;
import com.book.model.BookDetails;
import com.book.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Optional<Book> checkBook = bookRepo.findById(book.getId());
         if(checkBook.isPresent()){
             throw CustomException.with(ExceptionEnum.Book_Is_Available);
         }else {
             return bookRepo.save(book);
         }
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

    public List<Book> addBooks(List<Book> books){
        return bookRepo.saveAll(books);
    }

//    public List<BookDetails> getBookDetails(){
//        List<Object[]> books = bookRepo.getBookDetails();
//        List<BookDetails> bookDetails = new ArrayList<>();
//        books.forEach(book->{
//            BookDetails bookDet = new BookDetails();
//            bookDet.setPublisher(book[0]+"");
//            bookDet.setBooks(Stream.of(book[1].toString().split(",")).collect(Collectors.toList()));
//            bookDetails.add(bookDet);
//        });
//        return bookDetails;
//    }

    public APIResponseData<List<Book>> bookSearch(String word){
       List<Book> books = bookRepo.findByNameContaining(word);
       if(!books.isEmpty()){
           return APIResponseData.<List<Book>>builder()
                   .success(true)
                   .data(books)
                   .build();
       }else{
           throw CustomException.with(ExceptionEnum.Book_Not_Found);
       }
    }

    public APIResponseData<List<Book>> top3Books(){
        List<Book> books = bookRepo.findAll();
        List<Book> collect = books.stream().sorted(Comparator.comparing(Book::getPrice).reversed()).limit(3).collect(Collectors.toList());
        return APIResponseData.<List<Book>>builder()
                    .success(true)
                    .data(collect)
                    .build();
    }

    public APIResponseData<Book> maximumSales(int id1,int id2){
       Optional<Book> book1 = bookRepo.findById(id1);
       Optional<Book> book2 = bookRepo.findById(id2);
       if(book1.isPresent() && book2.isPresent()){
           if(book1.get().getSales()>book2.get().getSales()){
               return APIResponseData.<Book>builder()
                       .success(true)
                       .data(book1.get())
                       .build();
           }else{
               return APIResponseData.<Book>builder()
                       .success(true)
                       .data(book2.get())
                       .build();
           }
       }else{
           throw CustomException.with(ExceptionEnum.Book_Not_Found);
       }
    }

    public APIResponseData<List<Book>> checkMaximumSales(List<Book> books,Predicate<Book> book){
        List<Book> checkedBooks = new ArrayList<>();
        books.forEach(boo -> {
            if(book.test(boo)){
                checkedBooks.add(boo);
            };
        });
        return APIResponseData.<List<Book>>builder()
                .success(true)
                .data(checkedBooks)
                .build();
    }


}
