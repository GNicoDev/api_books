package com.books.books.sevice;

import com.books.books.entity.Book;
import com.books.books.entity.Genre;
import com.books.books.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class BookServiceImpl implements BookService{
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> listAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> listAllBooksByGenre(Genre genre) {
        List<Book> bookList = bookRepository.findAll().stream().
                filter(book -> book.getGenre()== genre).collect(Collectors.toList());
        return bookList;
    }


    @Override
    public List<Book> findBookByName(String name) {
        List<Book> bookList = bookRepository.findAll().stream().
                filter(book -> book.getName()==name).collect(Collectors.toList());
        return bookList;
    }

    @Override
    public Book saveBook(Book newBook) {
        return bookRepository.save(newBook);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
