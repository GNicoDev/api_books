package com.books.books.controller;

import com.books.books.controller.dto.BookDto;
import com.books.books.entity.Genre;
import com.books.books.sevice.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/book")
public class BookController {

    BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("allbooks")
    public ResponseEntity<?> listAllBooks() {
        List<BookDto> bookListDto = bookService.listAllBooks();
        return ResponseEntity.ok(bookListDto);
    }

    @GetMapping("listbooksbygender/{value}")
    public ResponseEntity<?> listBooksByGenre(@PathVariable String value) {
        Genre genre = Genre.convertValue(value);
        if (genre != null) {
            List<BookDto> bookListDto = bookService.listAllBooksByGenre(genre);
            return ResponseEntity.ok(bookListDto);
        } else return ResponseEntity.badRequest().body("Genre not exist");
    }

    @PutMapping("/updatebook/{id}")
    public ResponseEntity<?> updateBook (@RequestBody BookDto bookDto,@PathVariable Long id){
        Optional<BookDto> bookDtoActualized = bookService.updateBook(bookDto,id);

        if (bookDtoActualized.isPresent()){
            return ResponseEntity.ok(bookDtoActualized.get());
        }
        return ResponseEntity.badRequest().body("incorrect Data");
    }

    @PostMapping("/newbook")
    public ResponseEntity<?> newBook(@Valid @RequestBody BookDto bookDto) {
        try {
            return ResponseEntity.ok(bookService.saveBook(bookDto));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("incomplete fields");
        }
    }

    @DeleteMapping("deletebook/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        if(bookService.idExist(id)) {
            bookService.deleteBookById(id);
            return ResponseEntity.ok("Registro Eliminado");
        }
        else return ResponseEntity.badRequest().body("Id not exist");
    }
}
