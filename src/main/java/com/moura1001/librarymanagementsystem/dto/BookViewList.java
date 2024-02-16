package com.moura1001.librarymanagementsystem.dto;

import com.moura1001.librarymanagementsystem.model.Book;
import com.moura1001.librarymanagementsystem.model.GenreType;

public record BookViewList(
        String title,
        String isbn,
        GenreType genre
) {
    public BookViewList(Book book) {
        this(
                book.getTitle(),
                book.getIsbn(),
                book.getGenre()
        );
    }
}
