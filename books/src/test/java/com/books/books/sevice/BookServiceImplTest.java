package com.books.books.sevice;

import com.books.books.controller.dto.BookDto;
import com.books.books.entity.Book;
import com.books.books.entity.Genre;
import com.books.books.repository.BookRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class BookServiceImplTest {
    @Mock
    BookRepository bookRepository;
    @InjectMocks
    BookServiceImpl bookService;

    Book book01, book02, book03;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        book01 = new Book(1L,"El Aleph","Borges", Genre.DRAMA, 5600.50);
        book02 = new Book(2L,"El Proceso","Kafka", Genre.THRILLER, 6600.50);
        book03 = new Book(3L,"Rayuela","Cortazar", Genre.ADVENTURE, 5800.50);
    }

    @Test
    void listAllBooks() {
        Mockito.when(bookRepository.findAll()).thenReturn(Arrays.asList(book01,book02,book03));

        assertNotNull(bookService.listAllBooks());
    }
////////////////////////////////////////////////////////////////////////////////////
    @Test
    void listAllBooksByGenre() {
        Mockito.when(bookRepository.findAll()).thenReturn(Arrays.asList(book01,book02,book03));

        List<Book> bookList = bookService.listAllBooksByGenre(Genre.DRAMA);

        List<Long> idsBookselectByGenre = bookList.stream().map(book -> book.getId()).collect(Collectors.toList());

        Assert.assertThat(idsBookselectByGenre, CoreMatchers.is(Arrays.asList(1L)));
    }


////////////////////////////////////////////////////////////////////////////////////
    @Test
    void findBookByName() {
        Mockito.when(bookRepository.findAll()).thenReturn(Arrays.asList(book01,book02,book03));

        List<Book> bookList = bookService.findBookByName("El Proceso");

        List<Long> idsBookselectByGenre = bookList.stream().map(book -> book.getId()).collect(Collectors.toList());

        Assert.assertThat(idsBookselectByGenre, CoreMatchers.is(Arrays.asList(2L)));
    }


////////////////////////////////////////////////////////////////////////////////////
    @Test
    void saveBook() {
        Mockito.when(bookRepository.save(book01)).thenReturn(book01);

        Book savedBook = bookService.saveBook(book01);

        assertNotNull(savedBook);

        assertEquals(book01,savedBook);

        System.out.println(" ID Book saved: "+ savedBook.getId());
    }
////////////////////////////////////////////////////////////////////////////////////
    @Test
    void deleteBookById() {
        Mockito.doNothing().when(bookRepository).deleteById(book01.getId());
        bookService.deleteBookById(book01.getId());
        verify(bookRepository).deleteById(any());
    }

////////////////////////////////////////////////////////////////////////////////////

    @Test
    void dtoIsOk_return_true_when_all_attributes_are_complete(){
        BookDto bookDto = BookDto.builder()
                .name("El Proceso")
                .genre(Genre.DRAMA)
                .author("Kafka")
                .price(5600.0)
                .build();
        boolean isOk = bookService.dtoIsOk(bookDto);

        assertTrue(isOk);
    }

    @Test
    void dtoIsOk_return_false_when_someone_attribute_is_null(){
        BookDto bookDto = BookDto.builder()
                .name("El Proceso")
                .genre(null)
                .author("Kafka")
                .price(5600.0)
                .build();
        boolean isOk = bookService.dtoIsOk(bookDto);

        assertFalse(isOk);
    }

    @Test
    void dtoIsOk_return_false_when_bookDto_is_null(){
        BookDto bookDto = null;
        boolean isOk = bookService.dtoIsOk(bookDto);
        assertFalse(isOk);
    }
////////////////////////////////////////////////////////////////////////////////////

}