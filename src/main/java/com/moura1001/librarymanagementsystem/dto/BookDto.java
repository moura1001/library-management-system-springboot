package com.moura1001.librarymanagementsystem.dto;

import com.moura1001.librarymanagementsystem.model.Book;
import com.moura1001.librarymanagementsystem.model.GenreType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookDto(
        @NotEmpty @Size(min = 8, max = 2048) String title,
        @NotEmpty @Size(min = 3, max = 512) String author,
        @NotEmpty @Size(min = 13, max = 13) String isbn,
        @NotNull GenreType genre
) {
    public Book toEntity() {
        return new Book(
                this.title,
                this.author,
                this.isbn,
                this.genre
        );
    }
}
