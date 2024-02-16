package com.moura1001.librarymanagementsystem.service;

import com.moura1001.librarymanagementsystem.exception.BookException;
import com.moura1001.librarymanagementsystem.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getAllBooks() throws BookException {
        try {
            return repository.findAll();
        } catch (RuntimeException e) {
            throw new BookException("internal error: " + e.getMessage());
        }
    }

    public Book findBookById(Long id) throws BookException {
        return repository.findById(id).orElseThrow(() -> new BookException("book not found"));
    }

    public Book findBookByTitle(String title) throws BookException {
        return repository.findBookByTitle(title).orElseThrow(() -> new BookException("book not found"));
    }

    public List<Book> findBookByAuthor(String author) throws BookException {
        try {
            return repository.findBookByAuthor(author);
        } catch (RuntimeException e) {
            throw new BookException("internal error: " + e.getMessage());
        }
    }

    public long countAuthorBooks(String author) throws BookException {
        try {
            return repository.countAuthorBooks(author);
        } catch (RuntimeException e) {
            throw new BookException("internal error: " + e.getMessage());
        }
    }

    public Book saveBook(Book book) throws BookException {
        try {
            return repository.save(book);
        } catch (RuntimeException e) {
            throw new BookException("internal error: " + e.getMessage());
        }
    }

    public void saveAllBooks(List<Book> books) throws BookException {
        try {
            repository.saveAll(books);
        } catch (RuntimeException e) {
            throw new BookException("internal error: " + e.getMessage());
        }
    }

    public void deleteBook(Long id) throws BookException {
        if(!repository.existsById(id)) {
            throw new BookException("book not found");
        }

        try {
            repository.deleteById(id);
        } catch (RuntimeException e) {
            throw new BookException("internal error: " + e.getMessage());
        }
    }
}
