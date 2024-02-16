package com.moura1001.librarymanagementsystem.service;

import com.moura1001.librarymanagementsystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookByTitle(String title);

    List<Book> findBookByAuthor(String author);

    @Query("SELECT COUNT(b.id) FROM books b WHERE b.author = ?1")
    long countAuthorBooks(String author);
}
