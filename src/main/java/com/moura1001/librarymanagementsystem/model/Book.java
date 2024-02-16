package com.moura1001.librarymanagementsystem.model;

import jakarta.persistence.*;

@Entity(name = "books")
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2048)
    private String title;

    @Column(nullable = false, length = 512)
    private String author;

    @Column(nullable = false, length = 13)
    private String isbn;

    @Column(nullable = false)
    @Enumerated
    private GenreType genre;

    public Book() {
    }

    public Book(Long id, String title, String author, String isbn, GenreType genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public GenreType getGenre() {
        return genre;
    }
}
