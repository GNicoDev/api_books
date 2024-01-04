package com.books.books.entity;

import com.books.books.entity.Genre;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (length = 20, nullable = false)
    private String name;

    @Column (length = 20, nullable = false)
    private String author;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private Genre genre;

    @Column (nullable = false)
    private double price;
}
