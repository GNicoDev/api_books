package com.books.books.controller;

import com.books.books.controller.dto.BookDto;
import com.books.books.entity.Book;
import com.books.books.entity.Genre;
import com.books.books.sevice.BookService;
import com.books.books.sevice.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.hamcrest.Matchers;

import java.util.List;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @MockBean
    private BookServiceImpl bookService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    private Book book;

    private BookDto bookDto;

/*
    @BeforeEach
    void setUpMockMvc (){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }   */
/*    @BeforeEach
    void setUp() {
        bookDto =BookDto.builder()
                .name("El Aleph")
                .author("Cortazar")
                .genre(Genre.DRAMA)
                .price(6800.59)
                .build();

        book =Book.builder()
                .id(1L)
                .name("El Proceso")
                .author("Kafka")
                .genre(Genre.THRILLER)
                .price(8500.0)
                .build();
    }   */

    @Test
    void shouldCreateMockMvc(){
        assertNotNull(mockMvc);
    }

/////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void should_return_listAllBooks() throws Exception {
        when(bookService.listAllBooks()).thenReturn(List.of(new Book(1L,"El Aleph","Borges", Genre.THRILLER,5600.0)));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/book/allbooks"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("El Aleph"));
    }
    /////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void listBooksByGenre_should_return_status_OK() throws Exception {

        when(bookService.listAllBooksByGenre(Genre.THRILLER)).thenReturn(List.of(new Book(1L,"El Aleph","Borges", Genre.THRILLER,5600.0)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/book/listbooksbygender/THRILLER"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("El Aleph"));

    }

    @Test
    void listBooksByGenre_should_return_status_BADREQUEST_because_Genre_not_exist() throws Exception {


        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/book/listbooksbygender/COMEDY"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    /////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void should_create_newBook() throws Exception {
 //       Boolean value = bookService.dtoIsOk(bookDto);
//        System.out.println(value);
//        Mockito.when(bookService.dtoIsOk(BookDto.builder().build())).thenReturn(true);
        //when(bookService.saveBook(bookDto)).thenReturn(bookDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/book/newbook")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"El Aleph\", \"author\": \"Borges\", \"genre\" : \"DRAMA\", \"price\" : \"6800.59\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    /////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void deleteBook() throws Exception {
        Mockito.doNothing().when(bookService).deleteBookById(1L);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/book/deletebook/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}