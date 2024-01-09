package com.books.books.sevice;

import com.books.books.controller.dto.BookDto;
import com.books.books.entity.Book;
import com.books.books.entity.Genre;
import com.books.books.repository.BookRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceImplTest {
    @Mock
    BookRepository bookRepository;
    @InjectMocks
    BookServiceImpl bookService;

    Book book01, book02, book03;

    BookDto bookDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        book01 = new Book(1L,"El Aleph","Borges", Genre.DRAMA, 5600.50);
        book02 = new Book(2L,"El Proceso","Kafka", Genre.THRILLER, 6600.50);
        book03 = new Book(3L,"Rayuela","Cortazar", Genre.ADVENTURE, 5800.50);

        bookDto = BookDto.builder()
                .name(book01.getName())
                .author(book01.getAuthor())
                .genre(book01.getGenre())
                .price(book01.getPrice())
                .build();
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

        List<BookDto> bookList = bookService.listAllBooksByGenre(Genre.DRAMA);

        List<Long> idsBookselectByGenre = bookList.stream().map(book -> book.getId()).collect(Collectors.toList());

        Assert.assertThat(idsBookselectByGenre, CoreMatchers.is(Arrays.asList(1L)));
    }


////////////////////////////////////////////////////////////////////////////////////
    @Test
    void findBookByName() {
        Mockito.when(bookRepository.findAll()).thenReturn(Arrays.asList(book01,book02,book03));

        List<BookDto> bookList = bookService.findBookByName("El Proceso");

        List<Long> idsBookselectByGenre = bookList.stream().map(book -> book.getId()).collect(Collectors.toList());

        Assert.assertThat(idsBookselectByGenre, CoreMatchers.is(Arrays.asList(2L)));
    }


////////////////////////////////////////////////////////////////////////////////////
    @Test
    void saveBook() {
        Mockito.when(bookRepository.save(book01)).thenReturn(book01);

        BookDto savedBook = bookService.saveBook(bookDto);

        assertNotNull(savedBook);

        assertEquals(book01.getAuthor(),savedBook.getAuthor());

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
    void idExist_should_return_true_if_id_exist() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        Boolean value = bookService.idExist(1L);
        assertTrue(value);
    }

    @Test
    void idExist_should_return_false_if_id_not_exist() {
        when(bookRepository.existsById(8L)).thenReturn(false);
        Boolean value = bookService.idExist(8L);
        assertFalse(value);

    }

    @Test
    void idExist_should_return_false_because_id_is_null() {
        Boolean value = bookService.idExist(null);
        assertFalse(value);
    }

////////////////////////////////////////////////////////////////////////////////////

    @Test
    void updateBook_should_return_bookDto_when_id_exist() {
        BookDto putBookDto = BookDto.builder()
                .price(7890.0)
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book01));

        Optional<BookDto> bookDto1 = bookService.updateBook(putBookDto,1L);

        assertEquals(bookDto1.get().getPrice(),book01.getPrice());
        assertEquals(bookDto1.get().getName(),book01.getName());

    }

    @Test
    void updateBook_should_return_null_when_id__not_exist() {
        BookDto putBookDto = BookDto.builder()
                .price(7890.0)
                .build();

        when(bookRepository.findById(8L)).thenReturn(Optional.empty());

        Optional<BookDto> bookDto1 = bookService.updateBook(putBookDto,8L);

        assertEquals(Optional.empty(),bookDto1);
    }
////////////////////////////////////////////////////////////////////////////////////

}