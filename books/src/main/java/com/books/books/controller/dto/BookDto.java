package com.books.books.controller.dto;

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

    private double price;
}
