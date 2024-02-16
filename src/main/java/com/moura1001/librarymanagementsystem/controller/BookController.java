package com.moura1001.librarymanagementsystem.controller;

import com.moura1001.librarymanagementsystem.dto.BookDto;
import com.moura1001.librarymanagementsystem.dto.BookUpdateDto;
import com.moura1001.librarymanagementsystem.dto.BookView;
import com.moura1001.librarymanagementsystem.dto.BookViewList;
import com.moura1001.librarymanagementsystem.model.Book;
import com.moura1001.librarymanagementsystem.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BookView> saveBook(@RequestBody @Valid BookDto bookDto) {
        Book savedBook = this.service.saveBook(bookDto.toEntity());
        return ResponseEntity.created(
                URI.create("/api/books/"+savedBook.getId())
        ).body(new BookView(savedBook));
    }

    @GetMapping("/search")
    public ResponseEntity<BookView> findBookByTitle(@RequestParam(value = "title") String title) {
        Book book = this.service.findBookByTitle(title);
        return ResponseEntity.ok(new BookView(book));
    }

    @GetMapping
    public ResponseEntity<List<BookViewList>> findBookByAuthor(@RequestParam(value = "author") String author) {
        List<BookViewList> books = this.service.findBookByAuthor(author).stream()
                .map((book) -> new BookViewList(book)).collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    @PatchMapping("{id}")
    public ResponseEntity<BookView> updateBook(
            @PathVariable Long id,
            @RequestBody @Valid BookUpdateDto bookDto
    ) {
        Book book = this.service.findBookById(id);
        Book updatedBook = this.service.saveBook(bookDto.toEntity(book));
        return ResponseEntity.ok(new BookView(updatedBook));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        this.service.deleteBook(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("book successfully deleted");
    }
}
