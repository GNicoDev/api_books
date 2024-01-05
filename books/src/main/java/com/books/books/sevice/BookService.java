package com.books.books.sevice;
import com.books.books.controller.dto.BookDto;
import com.books.books.entity.*;

import java.util.List;

public interface BookService {
    List<Book> listAllBooks();

    List<Book> listAllBooksByGenre(Genre genre);

    List<Book> findBookByName(String name);

    Book saveBook(Book newBook);

    void deleteBookById(Long id);

    boolean dtoIsOk(BookDto bookDto);

}
