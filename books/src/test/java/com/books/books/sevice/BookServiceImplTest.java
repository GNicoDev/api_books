package com.books.books.sevice;

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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(MockitoJUnitRunner.class)
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
    }
////////////////////////////////////////////////////////////////////////////////////
    @Test
    void deleteBookById() {
        // bookRepository.deleteById(book01.getId());
        //verify(bookRepository).deleteById(book01.getId()); // check that the method was called
        Mockito.doNothing().when(bookRepository).deleteById(book01.getId());
        Mockito.when(bookRepository.findAll()).thenReturn(Arrays.asList(book02,book03));


        bookService.deleteBookById(book01.getId());
        List<Book> bookList = bookService.listAllBooks();

        List<Long> idsBookselectByGenre = bookList.stream().map(book -> book.getId()).collect(Collectors.toList());

        Assert.assertThat(idsBookselectByGenre, CoreMatchers.is(Arrays.asList(2L,3L)));
    }
}