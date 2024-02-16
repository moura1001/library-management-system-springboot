package com.moura1001.librarymanagementsystem.utils;

import com.moura1001.librarymanagementsystem.dto.BookDto;
import com.moura1001.librarymanagementsystem.dto.BookUpdateDto;
import com.moura1001.librarymanagementsystem.model.Book;
import com.moura1001.librarymanagementsystem.model.GenreType;

import java.util.List;

public class EntityBuilder {
    public static Book buildBook() {
        return EntityBuilder.buildBook(
                null,
                "The Silmarillion",
                "J. R. R. Tolkien",
                "9788533611658",
                GenreType.FANTASY
        );
    }

    public static Book buildBook(Long id, String title, String author, String isbn, GenreType genre) {
        return new Book(id, title, author, isbn, genre);
    }

    public static List<Book> buildBookList() {
        return List.of(
                EntityBuilder.buildBook(),
                EntityBuilder.buildBook(
                        null,
                        "Unfinished Tales",
                        "J. R. R. Tolkien",
                        "9780048231796",
                        GenreType.FANTASY
                )
        );
    }

    public static BookDto buildBookDto() {
        return EntityBuilder.buildBookDto(
                "The Silmarillion",
                "J. R. R. Tolkien",
                "9788533611658",
                GenreType.FANTASY
        );
    }

    public static BookDto buildBookDto(String title, String author, String isbn, GenreType genre) {
        return new BookDto(title, author, isbn, genre);
    }

    public static BookUpdateDto buildBookUpdateDto() {
        return EntityBuilder.buildBookUpdateDto(
                "853361165X",
                GenreType.FANTASY
        );
    }

    public static BookUpdateDto buildBookUpdateDto(String isbn, GenreType genre) {
        return new BookUpdateDto(isbn, genre);
    }
}
