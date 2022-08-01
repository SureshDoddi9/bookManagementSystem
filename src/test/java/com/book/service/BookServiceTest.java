package com.book.service;

import com.book.exception.CustomException;
import com.book.model.APIResponseData;
import com.book.model.Book;
import com.book.repo.BookRepo;
import com.book.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    BookRepo bookRepo;

    @InjectMocks
    BookService bookService;

    @Test
    void getBookTestSuccess(){
        Optional<Book> book = Optional.ofNullable(TestUtil.getBook());
        APIResponseData<Book> expResponse = APIResponseData.<Book>builder()
                .success(true)
                .data(book.get())
                .build();
        Mockito.when(bookRepo.findById(anyInt())).thenReturn(book);
        APIResponseData<Book> actualResponse = bookService.getBook(101);
        Assertions.assertEquals(expResponse.getData().getId(),actualResponse.getData().getId());
        Assertions.assertEquals(expResponse.getData().getAuthor(),actualResponse.getData().getAuthor());
        Assertions.assertEquals(expResponse.getData().getName(),actualResponse.getData().getName());
        Assertions.assertEquals(expResponse.getData().getDescription(),actualResponse.getData().getDescription());
        Assertions.assertEquals(expResponse.getData().getPrice(),actualResponse.getData().getPrice());
        Assertions.assertEquals(expResponse.getData().getPublisher(),actualResponse.getData().getPublisher());
        Assertions.assertEquals(expResponse.getData().getQuantity(),actualResponse.getData().getQuantity());
        Assertions.assertEquals(expResponse.getData().getSales(),actualResponse.getData().getSales());
        Assertions.assertEquals(expResponse.isSuccess(),actualResponse.isSuccess());
        Mockito.verify(bookRepo).findById(anyInt());

    }

    @Test
    void getBookTestError(){
        Optional<Book> book = Optional.empty();
        Mockito.when(bookRepo.findById(anyInt())).thenReturn(book);
        org.assertj.core.api.Assertions.assertThatExceptionOfType(CustomException.class)
                .isThrownBy(() ->bookService.getBook(101))
                .withMessageContaining("Given Book is not found in the database");

    }

    @Test
    void getBooksTest(){
        List<Book> books = new ArrayList<>(Arrays.asList(TestUtil.getBook()));
        APIResponseData<List<Book>> expResponse = APIResponseData.<List<Book>>builder()
                .success(true)
                .data(books)
                .build();
        Mockito.when(bookRepo.findAll()).thenReturn(books);
        APIResponseData<List<Book>> actualResponse = bookService.getBooks();
        Assertions.assertEquals(expResponse.getData().get(0).getId(),actualResponse.getData().get(0).getId());
        Assertions.assertEquals(expResponse.getData().get(0).getAuthor(),actualResponse.getData().get(0).getAuthor());
        Assertions.assertEquals(expResponse.getData().get(0).getName(),actualResponse.getData().get(0).getName());
        Assertions.assertEquals(expResponse.getData().get(0).getDescription(),actualResponse.getData().get(0).getDescription());
        Assertions.assertEquals(expResponse.getData().get(0).getPrice(),actualResponse.getData().get(0).getPrice());
        Assertions.assertEquals(expResponse.getData().get(0).getPublisher(),actualResponse.getData().get(0).getPublisher());
        Assertions.assertEquals(expResponse.getData().get(0).getQuantity(),actualResponse.getData().get(0).getQuantity());
        Assertions.assertEquals(expResponse.getData().get(0).getSales(),actualResponse.getData().get(0).getSales());
        Assertions.assertEquals(expResponse.isSuccess(),actualResponse.isSuccess());
        Mockito.verify(bookRepo,Mockito.times(1)).findAll();

    }

    @Test
    void createBook(){
        Optional<Book> book = Optional.empty();
        Mockito.when(bookRepo.findById(anyInt())).thenReturn(book);
        Mockito.when(bookRepo.save(any(Book.class))).thenReturn(TestUtil.getBook());
        Book response = bookService.createBook(TestUtil.getBook());
        Assertions.assertEquals(TestUtil.getBook().getId(),response.getId());
        Assertions.assertEquals(TestUtil.getBook().getName(),response.getName());
        Assertions.assertEquals(TestUtil.getBook().getDescription(),response.getDescription());
        Assertions.assertEquals(TestUtil.getBook().getPublisher(),response.getPublisher());
        Assertions.assertEquals(TestUtil.getBook().getPrice(),response.getPrice());
        Assertions.assertEquals(TestUtil.getBook().getQuantity(),response.getQuantity());
        Assertions.assertEquals(TestUtil.getBook().getAuthor(),response.getAuthor());
        Mockito.verify(bookRepo,Mockito.times(1)).findById(anyInt());
    }

    @Test
    void createBookErrorTest(){
        Optional<Book> book = Optional.ofNullable(TestUtil.getBook());
        Mockito.when(bookRepo.findById(anyInt())).thenReturn(book);
        org.assertj.core.api.Assertions.assertThatExceptionOfType(CustomException.class)
                .isThrownBy(() ->bookService.createBook(book.get()))
                .withMessageContaining("The Book with same Id already available");

    }

    @Test
    void updateBookTest(){
        Optional<Book> book = Optional.ofNullable(TestUtil.getBook());
        Mockito.when(bookRepo.findByName(anyString())).thenReturn(book);
        Mockito.when(bookRepo.save(any(Book.class))).thenReturn(book.get());
        Map<String,String> map = bookService.updateBook(TestUtil.getBook());
        Assertions.assertEquals(map.get("status"),"Book Updated Successfullfy");
        Mockito.verify(bookRepo,Mockito.times(1)).findByName(anyString());
        Mockito.verify(bookRepo,Mockito.times(1)).save(any(Book.class));
    }

    @Test
    void updateBookErrorTest(){
       Optional<Book> book = Optional.empty();
       Mockito.when(bookRepo.findByName(anyString())).thenReturn(book);
        org.assertj.core.api.Assertions.assertThatExceptionOfType(CustomException.class)
                .isThrownBy(()->bookService.updateBook(TestUtil.getBook()))
                .withMessageContaining("Given Book is not found in the database");
    }

    @Test
    void deleteBookTest(){
        Optional<Book> book = Optional.ofNullable(TestUtil.getBook());
        Mockito.when(bookRepo.findById(anyInt())).thenReturn(book);
        Map<String,String> map = bookService.deleteBook(101);
        Assertions.assertEquals(map.get("status"),"Book Deleted Successfullfy");
        Mockito.verify(bookRepo,Mockito.times(1)).findById(anyInt());
        Mockito.verify(bookRepo,Mockito.times(1)).delete(any(Book.class));
    }

    @Test
    void deleteBookErrorTest(){
        Optional<Book> book = Optional.empty();
        Mockito.when(bookRepo.findById(anyInt())).thenReturn(book);
        org.assertj.core.api.Assertions.assertThatExceptionOfType(CustomException.class)
                .isThrownBy(()->bookService.deleteBook(101))
                .withMessageContaining("Given Book is not found in the database");
    }

//    @Test
//    void getBookDetailsTest(){
//        Object[] obj = new Object[];
//        List<Object[]> books = new ArrayList<>(Arrays.asList());
//    }

}
