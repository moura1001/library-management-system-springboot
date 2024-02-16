package com.moura1001.librarymanagementsystem.dto;

import com.moura1001.librarymanagementsystem.model.Book;
import com.moura1001.librarymanagementsystem.model.GenreType;

public record BookView(
        String title,
        String author,
        String isbn,
        GenreType genre
) {
    public BookView(Book book) {
        this(
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getGenre()
        );
    }
}
