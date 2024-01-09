package com.books.books.sevice;
import com.books.books.controller.dto.BookDto;
import com.books.books.entity.*;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDto> listAllBooks();

    List<BookDto> listAllBooksByGenre(Genre genre);

    List<BookDto> findBookByName(String name);

    BookDto saveBook(BookDto newBook);

    Optional<BookDto> updateBook(BookDto bookDto, Long id);

    void deleteBookById(Long id);

    Boolean idExist(Long id);
}
