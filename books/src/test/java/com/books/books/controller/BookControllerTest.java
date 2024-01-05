package com.books.books.controller;

import com.books.books.controller.dto.BookDto;
import com.books.books.entity.Book;
import com.books.books.entity.Genre;
import com.books.books.sevice.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    BookController bookController = new BookController(bookService);

    private Book book, postBook;

    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        book = Book.builder()
                .id(1L)
                .author("Borges")
                .genre(Genre.DRAMA)
                .name("El Aleph")
                .price(5600.90)
                .build();

        bookDto = BookDto.builder()
                .author("Borges")
                .genre(Genre.DRAMA)
                .name("El Aleph")
                .price(5600.90)
                .build();

        postBook = Book.builder()
                .author("Borges")
                .genre(Genre.DRAMA)
                .name("El Aleph")
                .price(5600.90)
                .build();
    }

/////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void listAllBooks() {
        Mockito.when(bookService.listAllBooks()).thenReturn(Arrays.asList(book));
        ResponseEntity<?> serviceResponse;
        serviceResponse = bookController.listAllBooks();
        System.out.println(serviceResponse);
    }

/////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void listBooksByGenre() throws Exception {

        Mockito.when(bookService.listAllBooksByGenre(Genre.DRAMA)).thenReturn(Arrays.asList(book));
        ResponseEntity<?> serviceResponse;
        serviceResponse = bookController.listBooksByGenre("DRAMA");
        System.out.println(serviceResponse);
    }

    @Test
    void listBooksByGenre_return_BADREQUEST_because_the_genre_not_exist() throws Exception {

        ResponseEntity<?> serviceResponse;
        serviceResponse = bookController.listBooksByGenre("COMEDY");
        System.out.println(serviceResponse);
    }

/////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void newBook_return_OK() throws Exception {

        Mockito.when(bookService.saveBook(postBook)).thenReturn(book);
        Mockito.when(bookService.dtoIsOk(bookDto)).thenReturn(true);

        ResponseEntity<BookDto> serviceResponse;
        serviceResponse = bookController.newBook(bookDto);
        System.out.println(serviceResponse);
    }

    @Test
    void newBook_return_BAD_Request() throws Exception {

        Mockito.when(bookService.saveBook(postBook)).thenReturn(book);

        ResponseEntity<BookDto> serviceResponse;
        serviceResponse = bookController.newBook(null);
        System.out.println(serviceResponse);
    }

    @Test
    void newBook_return_BADREQUEST_when_bookDto_is_incomplete() throws Exception {
        bookDto.setAuthor(null);
        Mockito.when(bookService.saveBook(postBook)).thenReturn(book);

        ResponseEntity<BookDto> serviceResponse;
        serviceResponse = bookController.newBook(bookDto);
        System.out.println(serviceResponse);
    }
/////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void deleteBook() {
        Mockito.doNothing().when(bookService).deleteBookById(book.getId());
        ResponseEntity<?> serviceResponse;
        serviceResponse = bookController.deleteBook(1L);
        System.out.println(serviceResponse);
    }
}