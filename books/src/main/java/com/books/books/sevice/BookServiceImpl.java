package com.books.books.sevice;

import com.books.books.controller.dto.BookDto;
import com.books.books.entity.Book;
import com.books.books.entity.Genre;
import com.books.books.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;

import java.util.Objects;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDto> listAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        List<BookDto> bookDtoList = new ArrayList<>();
        BookDto bookDto;
        for (Book b : bookList) {
            bookDto = new BookDto(b);
            bookDtoList.add(bookDto);
        }
        return bookDtoList;
    }

    @Override
    public List<BookDto> listAllBooksByGenre(Genre genre) {
        List<Book> bookList = bookRepository.findAll().stream().
                filter(book -> book.getGenre()== genre).toList();
        List<BookDto> bookDtoList = new ArrayList<>();
        BookDto bookDto;
        for (Book b : bookList) {
            bookDto = new BookDto(b);
            bookDtoList.add(bookDto);
        }
        return bookDtoList;
    }


    @Override
    public List<BookDto> findBookByName(String name) {
        List<Book> bookList = bookRepository.findAll().stream().
                filter(book -> book.getName()==name).toList();
        List<BookDto> bookDtoList = new ArrayList<>();
        BookDto bookDto;
        for (Book b : bookList) {
            bookDto = new BookDto(b);
            bookDtoList.add(bookDto);
        }
        return bookDtoList;

    }

    @Override
    public BookDto saveBook(BookDto newBook) {
        Book book = Book.builder()
                .name(newBook.getName())
                .author(newBook.getAuthor())
                .genre(newBook.getGenre())
                .price(newBook.getPrice())
                .build();
        bookRepository.save(book);
        newBook.setId(book.getId());
        return newBook;
    }

    @Override
    public Optional<BookDto> updateBook(BookDto bookDto, Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()){
            Book bookToUpdate = bookOptional.get();
            if(Objects.nonNull(bookDto.getName()) && !"".equalsIgnoreCase(bookDto.getName()))
                bookToUpdate.setName(bookDto.getName());
            if(Objects.nonNull(bookDto.getAuthor()) && !"".equalsIgnoreCase(bookDto.getAuthor()))
                    bookToUpdate.setAuthor(bookDto.getAuthor());
            if(Objects.nonNull(bookDto.getGenre()) && !"".equalsIgnoreCase(String.valueOf(bookDto.getGenre())))
                    bookToUpdate.setGenre(bookDto.getGenre());
            if(Objects.nonNull(bookDto.getPrice()) && !"".equalsIgnoreCase(String.valueOf(bookDto.getPrice())))
                bookToUpdate.setPrice(bookDto.getPrice());
            bookRepository.save(bookToUpdate);
            bookDto = new BookDto(bookToUpdate);
            return Optional.of(bookDto);
        }
        return Optional.empty();
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Boolean idExist(Long id) {
        if (id == null)
            return false;
        else
          if (bookRepository.existsById(id))
                return true;
        return false;
    }

}
