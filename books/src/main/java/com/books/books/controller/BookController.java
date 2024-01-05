package com.books.books.controller;

import com.books.books.controller.dto.BookDto;
import com.books.books.entity.Book;
import com.books.books.entity.Genre;
import com.books.books.sevice.BookService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("allbooks")
    public ResponseEntity<?> listAllBooks() {
        List<Book> bookList = bookService.listAllBooks();
        List<BookDto> bookDtoList = new ArrayList<>();
        BookDto bookDto;
        for (Book b : bookList) {
            bookDto = BookDto.builder()
                    .id(b.getId())
                    .author(b.getAuthor())
                    .genre(b.getGenre())
                    .name(b.getName())
                    .price(b.getPrice())
                    .build();
            bookDtoList.add(bookDto);
        }
        return ResponseEntity.ok(bookDtoList);
    }

    @GetMapping("listbooksbygender/{value}")
    public ResponseEntity<?> listBooksByGenre(@PathVariable String value) {
        Genre genre = Genre.convertValue(value);
        if (genre != null) {
            List<Book> bookList = bookService.listAllBooksByGenre(genre);
            List<BookDto> bookDtoList = new ArrayList<>();
            BookDto bookDto;
            for (Book b : bookList) {
                bookDto = BookDto.builder()
                        .id(b.getId())
                        .author(b.getAuthor())
                        .genre(b.getGenre())
                        .name(b.getName())
                        .price(b.getPrice())
                        .build();
                bookDtoList.add(bookDto);
            }
            return ResponseEntity.ok(bookDtoList);
        } else return ResponseEntity.badRequest().body("Genre not exist");
    }

    @PostMapping("/newbook")
    public ResponseEntity<BookDto> newBook(@RequestBody BookDto bookDto) {
        if (bookService.dtoIsOk(bookDto)) {
            Book book = Book.builder()
                    .name(bookDto.getName())
                    .author(bookDto.getAuthor())
                    .genre(bookDto.getGenre())
                    .price(bookDto.getPrice())
                    .build();
            bookService.saveBook(book);
            bookDto.setId(book.getId());
            return ResponseEntity.ok(bookDto);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("deletebook/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok("Registro Eliminado");
    }
}
