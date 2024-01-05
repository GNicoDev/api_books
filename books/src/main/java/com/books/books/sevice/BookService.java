package com.books.books.sevice;
import com.books.books.controller.dto.BookDto;
import com.books.books.entity.*;

import java.util.List;

public interface BookService {
    List<Book> listAllBooks();

    List<Book> listAllBooksByGenre(Genre genre);

    List<Book> findBookByName(String name);

    BookDto saveBook(BookDto newBook);

    void deleteBookById(Long id);

    Boolean dtoIsOk(BookDto bookDto);

}
