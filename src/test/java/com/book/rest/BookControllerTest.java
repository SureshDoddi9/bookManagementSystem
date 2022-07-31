package com.book.rest;

import com.book.controller.BookController;
import com.book.model.APIResponseData;
import com.book.model.Book;
import com.book.service.BookService;
import com.book.util.TestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void createBookTest() throws Exception {
       Book book = TestUtil.getBook();
       Mockito.when(bookService.createBook(book)).thenReturn(book);
       mockMvc.perform(post("/book/addBook")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(book)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(101))
               .andExpect(jsonPath("$.name").value("myBook"))
               .andExpect(jsonPath("$.author").value("Suresh"))
               .andExpect(jsonPath("$.description").value("Book About Suresh Biography"))
               .andExpect(jsonPath("$.price").value(15000F))
               .andExpect(jsonPath("$.quantity").value(15))
               .andExpect(jsonPath("$.publisher").value("Rk"))
               .andExpect(jsonPath("$.sales").value(20));
    }

    @Test
    void createBooksTest() throws Exception{
        List<Book> books = new ArrayList<>(Arrays.asList(TestUtil.getBook()));
        Mockito.when(bookService.addBooks(any(List.class))).thenReturn(books);
        mockMvc.perform(
                post("/book/addBooks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(books)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(101))
                .andExpect(jsonPath("$[0].name").value("myBook"))
                .andExpect(jsonPath("$[0].author").value("Suresh"))
                .andExpect(jsonPath("$[0].description").value("Book About Suresh Biography"))
                .andExpect(jsonPath("$[0].price").value(15000F))
                .andExpect(jsonPath("$[0].quantity").value(15))
                .andExpect(jsonPath("$[0].publisher").value("Rk"))
                .andExpect(jsonPath("$[0].sales").value(20));

    }

    @Test
    void updateBookTest() throws Exception{
        Book book = TestUtil.getBook();
        Map<String,String> map = new HashMap<>();
        map.put("status","Book Updated Successfullfy");
        Mockito.when(bookService.updateBook(any(Book.class))).thenReturn(map);
        mockMvc.perform(
                put("/book/updateBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Book Updated Successfullfy"));

    }

    @Test
    void deleteBookTest() throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("status","Book Deleted Successfullfy");
        Mockito.when(bookService.deleteBook(anyInt())).thenReturn(map);
        mockMvc.perform(
                delete("/book/deleteBook")
                        .param("id", String.valueOf(101)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Book Deleted Successfullfy"));

    }

    @Test
    void getBook() throws Exception{
       APIResponseData<Book> book = APIResponseData.<Book>builder()
                .success(true)
                .data(TestUtil.getBook())
                .build();
       Mockito.when(bookService.getBook(anyInt())).thenReturn(book);
       mockMvc.perform(
               get("/book/getBook")
                       .param("id", String.valueOf(101)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data.id").value(101))
               .andExpect(jsonPath("$.data.name").value("myBook"))
               .andExpect(jsonPath("$.data.author").value("Suresh"))
               .andExpect(jsonPath("$.data.description").value("Book About Suresh Biography"))
               .andExpect(jsonPath("$.data.price").value(15000F))
               .andExpect(jsonPath("$.data.quantity").value(15))
               .andExpect(jsonPath("$.data.publisher").value("Rk"))
               .andExpect(jsonPath("$.data.sales").value(20));
    }

    @Test
    void getAllBook() throws Exception{
        APIResponseData<List<Book>> books = APIResponseData.<List<Book>>builder()
                .success(true)
                .data(Arrays.asList(TestUtil.getBook()))
                .build();
        Mockito.when(bookService.getBooks()).thenReturn(books);
        mockMvc.perform(
                        get("/book/allBooks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(101))
                .andExpect(jsonPath("$.data[0].name").value("myBook"))
                .andExpect(jsonPath("$.data[0].author").value("Suresh"))
                .andExpect(jsonPath("$.data[0].description").value("Book About Suresh Biography"))
                .andExpect(jsonPath("$.data[0].price").value(15000F))
                .andExpect(jsonPath("$.data[0].quantity").value(15))
                .andExpect(jsonPath("$.data[0].publisher").value("Rk"))
                .andExpect(jsonPath("$.data[0].sales").value(20));
    }

    @Test
    void searchBook() throws Exception{
        APIResponseData<List<Book>> books = APIResponseData.<List<Book>>builder()
                .success(true)
                .data(Arrays.asList(TestUtil.getBook(),TestUtil.getBook2()))
                .build();
        Mockito.when(bookService.bookSearch(anyString())).thenReturn(books);
        mockMvc.perform(
                        get("/book/search")
                                .param("bookName", "myBook"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(101))
                .andExpect(jsonPath("$.data[0].name").value("myBook"))
                .andExpect(jsonPath("$.data[0].author").value("Suresh"))
                .andExpect(jsonPath("$.data[0].description").value("Book About Suresh Biography"))
                .andExpect(jsonPath("$.data[0].price").value(15000F))
                .andExpect(jsonPath("$.data[0].quantity").value(15))
                .andExpect(jsonPath("$.data[0].publisher").value("Rk"))
                .andExpect(jsonPath("$.data[0].sales").value(20))
                .andExpect(jsonPath("$.data[1].id").value(102))
                .andExpect(jsonPath("$.data[1].name").value("myBook"))
                .andExpect(jsonPath("$.data[1].author").value("Surya"))
                .andExpect(jsonPath("$.data[1].description").value("Book About Surya Biography"))
                .andExpect(jsonPath("$.data[1].price").value(15000F))
                .andExpect(jsonPath("$.data[1].quantity").value(25))
                .andExpect(jsonPath("$.data[1].publisher").value("Rk"))
                .andExpect(jsonPath("$.data[1].sales").value(10));
    }

    @Test
    void top3Books() throws Exception{
        APIResponseData<List<Book>> books = APIResponseData.<List<Book>>builder()
                .success(true)
                .data(Arrays.asList(TestUtil.getBook(),TestUtil.getBook2(),TestUtil.getBook3()))
                .build();
        Mockito.when(bookService.top3Books()).thenReturn(books);
        mockMvc.perform(
                        get("/book/top3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(101))
                .andExpect(jsonPath("$.data[0].name").value("myBook"))
                .andExpect(jsonPath("$.data[0].author").value("Suresh"))
                .andExpect(jsonPath("$.data[0].description").value("Book About Suresh Biography"))
                .andExpect(jsonPath("$.data[0].price").value(15000F))
                .andExpect(jsonPath("$.data[0].quantity").value(15))
                .andExpect(jsonPath("$.data[0].publisher").value("Rk"))
                .andExpect(jsonPath("$.data[0].sales").value(20))
                .andExpect(jsonPath("$.data[1].id").value(102))
                .andExpect(jsonPath("$.data[1].name").value("myBook"))
                .andExpect(jsonPath("$.data[1].author").value("Surya"))
                .andExpect(jsonPath("$.data[1].description").value("Book About Surya Biography"))
                .andExpect(jsonPath("$.data[1].price").value(15000F))
                .andExpect(jsonPath("$.data[1].quantity").value(25))
                .andExpect(jsonPath("$.data[1].publisher").value("Rk"))
                .andExpect(jsonPath("$.data[1].sales").value(10))
                .andExpect(jsonPath("$.data[2].id").value(103))
                .andExpect(jsonPath("$.data[2].name").value("Java"))
                .andExpect(jsonPath("$.data[2].author").value("Antony"))
                .andExpect(jsonPath("$.data[2].description").value("Book About Java"))
                .andExpect(jsonPath("$.data[2].price").value(15000F))
                .andExpect(jsonPath("$.data[2].quantity").value(35))
                .andExpect(jsonPath("$.data[2].publisher").value("Rk"))
                .andExpect(jsonPath("$.data[2].sales").value(20));
    }

    @Test
    void bookWithHighestSale() throws Exception{
        APIResponseData<Book> book = APIResponseData.<Book>builder()
                .success(true)
                .data(TestUtil.getBook())
                .build();
        Mockito.when(bookService.maximumSales(anyInt(),anyInt())).thenReturn(book);
        mockMvc.perform(
                        get("/book/highestSale")
                                .param("id1", String.valueOf(101))
                                .param("id2",String.valueOf(102)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(101))
                .andExpect(jsonPath("$.data.name").value("myBook"))
                .andExpect(jsonPath("$.data.author").value("Suresh"))
                .andExpect(jsonPath("$.data.description").value("Book About Suresh Biography"))
                .andExpect(jsonPath("$.data.price").value(15000F))
                .andExpect(jsonPath("$.data.quantity").value(15))
                .andExpect(jsonPath("$.data.publisher").value("Rk"))
                .andExpect(jsonPath("$.data.sales").value(20));
    }

}
