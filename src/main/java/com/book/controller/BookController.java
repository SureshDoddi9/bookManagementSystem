package com.book.controller;

import com.book.model.APIResponseData;
import com.book.model.Book;
import com.book.model.BookDetails;
import com.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    public BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<Book> createBook(@RequestBody(required = true)  Book book){
       return ResponseEntity.ok(bookService.createBook(book));
    }

    @PostMapping("/addBooks")
    public ResponseEntity<List<Book>> createBooks(@RequestBody(required = true) List<Book> books){
         return ResponseEntity.ok(bookService.addBooks(books));
    }

    @PutMapping("/updateBook")
    public ResponseEntity<Map<String,String>> updateBook(@RequestBody Book book){
        return ResponseEntity.ok(bookService.updateBook(book));
    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<Map<String,String>> deleteBook(@RequestParam int id){
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @GetMapping("/getBook")
    public ResponseEntity<APIResponseData<Book>> getBook(int id){
       return ResponseEntity.ok(bookService.getBook(id));
    }

    @GetMapping("/allBooks")
    public ResponseEntity<APIResponseData<List<Book>>> getallBooks(){
       return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping("/bookDetails")
    public ResponseEntity<List<BookDetails>> getbookDetails(){
        return ResponseEntity.ok(bookService.getBookDetails());
    }

    @GetMapping("/display")
    public String display(){
        return "welcome to Heroku";
    }
}
