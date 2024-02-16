package com.moura1001.librarymanagementsystem.dto;

import com.moura1001.librarymanagementsystem.model.Book;
import com.moura1001.librarymanagementsystem.model.GenreType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookUpdateDto(
        @NotEmpty @Size(min = 10, max = 13) String isbn,
        @NotNull GenreType genre
) {
    public Book toEntity(Book book) {
        return new Book(
                book.getTitle(),
                book.getAuthor(),
                this.isbn,
                this.genre
        );
    }
}
