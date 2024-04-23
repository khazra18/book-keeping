package com.bookkeeping.Controller;

import com.bookkeeping.Entity.Book;
import com.bookkeeping.Entity.Classification;
import com.bookkeeping.Service.BookServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookKeepingController.class)
class BookKeepingControllerTest {

    private Book book;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookServiceImpl bookService;

    @InjectMocks
    private BookKeepingController bookKeepingController;

    @BeforeEach
    void setupBookEntity() {
        book = Book.builder()
                .id(1)
                .name("Half girlfriend")
                .price(100.0)
                .author("Chetan bhagat")
                .isbn("ABC-1234")
                .description("About my girlfriend")
                .classification(Classification.MYSTERY)
                .build();
    }

    @Test
    public void createBookTest_Success() throws Exception {

        when(bookService.createBook(any(Book.class))).thenReturn(book);

        ResultActions expectedResult = mockMvc.perform(MockMvcRequestBuilders.post("/bookstore/1.0.0/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));

        expectedResult.andExpect(status().isOk());

    }

    @Test
    public void getAllBooksTest_Success() throws Exception {

        List<Book> actualBooksList = new ArrayList<>();
        actualBooksList.add(book);

        when(bookService.getAllBooks()).thenReturn(actualBooksList);

        ResultActions expectedResult = mockMvc.perform(MockMvcRequestBuilders.get("/bookstore/1.0.0/books")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualBooksList)));

        expectedResult.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));

    }
}