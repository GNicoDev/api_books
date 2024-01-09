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
import java.util.Optional;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void shouldCreateMockMvc(){
        assertNotNull(mockMvc);
    }

/////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void should_return_listAllBooks() throws Exception {
        when(bookService.listAllBooks()).thenReturn(List.of(new BookDto(1L,"El Aleph","Borges", Genre.THRILLER,5600.0)));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/book/allbooks"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("El Aleph"));
    }
    /////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void listBooksByGenre_should_return_status_OK() throws Exception {

        when(bookService.listAllBooksByGenre(Genre.THRILLER)).thenReturn(List.of(new BookDto(1L,"El Aleph","Borges", Genre.THRILLER,5600.0)));

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
    void deleteBook_return_OK_because_id_exist() throws Exception {
        when(bookService.idExist(1L)).thenReturn(true);
        Mockito.doNothing().when(bookService).deleteBookById(1L);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/book/deletebook/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void deleteBook_return_BadRequest_because_id_not_exist() throws Exception {
        when(bookService.idExist(8L)).thenReturn(false);
        Mockito.doNothing().when(bookService).deleteBookById(1L);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/book/deletebook/8"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void updateBook_should_return_BADREQUEST_when_id_not_exist() throws Exception {
        BookDto putBookDto = BookDto.builder()
                .price(7890.0)
                .build();

        when(bookService.updateBook(putBookDto,8L)).thenReturn(Optional.empty());


        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/book/updatebook/8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"price\" : \"8800.59\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }


}