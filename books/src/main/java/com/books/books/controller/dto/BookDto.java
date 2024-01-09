package com.books.books.controller.dto;

import com.books.books.entity.Book;
import com.books.books.entity.Genre;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;

    private String name;

    private String author;

    private Genre genre;

    private Double price;

    public BookDto (Book book){
        this.id = book.getId();
        this.name = book.getName();
        this.author = book.getAuthor();
        this.genre = book.getGenre();
        this.price = book.getPrice();
    }
}
