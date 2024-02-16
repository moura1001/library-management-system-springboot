package com.moura1001.librarymanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moura1001.librarymanagementsystem.dto.BookDto;
import com.moura1001.librarymanagementsystem.dto.BookUpdateDto;
import com.moura1001.librarymanagementsystem.model.Book;
import com.moura1001.librarymanagementsystem.model.GenreType;
import com.moura1001.librarymanagementsystem.service.BookRepository;
import com.moura1001.librarymanagementsystem.utils.EntityBuilder;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class BookControllerTest {

    private static final String URL = "/api/books";

    private BookRepository repository;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    public BookControllerTest(BookRepository repository, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.repository = repository;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void shouldSaveBookAndReturnHTTP201Status() throws Exception {
        // given
        BookDto bookDto = EntityBuilder.buildBookDto();
        String valueAsString = objectMapper.writeValueAsString(bookDto);

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(bookDto.title()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(bookDto.author()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.isbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookDto.genre().name()));
    }

    @Test
    void shouldNotSaveBookWithInvalidTitleAndReturnHTTP400Status() throws Exception {
        // given
        BookDto bookDto = EntityBuilder.buildBookDto(null, "a", "b", GenreType.DYSTOPIAN);
        String valueAsString = objectMapper.writeValueAsString(bookDto);

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(valueAsString))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldFindBookByTitleAndReturnHTTP200Status() throws Exception {
        // given
        Book book = repository.save(EntityBuilder.buildBook());

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get(URL+"/search?title="+book.getTitle())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(book.getGenre().name()));
    }

    @Test
    void shouldFindBookByAuthorAndReturnHTTP200Status() throws Exception {
        // given
        Book book = EntityBuilder.buildBook();
        List<Book> books = repository.saveAll(EntityBuilder.buildBookList());

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get(URL+"?author="+book.getAuthor())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void shouldUpdateBookAndReturnHTTP204Status() throws Exception {
        // given
        List<Book> books = repository.saveAll(EntityBuilder.buildBookList());
        Book book = books.get(0);
        BookUpdateDto bookUpdateDto = EntityBuilder.buildBookUpdateDto();
        String valueAsString = objectMapper.writeValueAsString(bookUpdateDto);

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.patch(URL+"/"+book.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(valueAsString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(bookUpdateDto.isbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookUpdateDto.genre().name()));
    }

    @Test
    void shouldDeleteBookByAuthorAndReturnHTTP204Status() throws Exception {
        // given
        List<Book> books = repository.saveAll(EntityBuilder.buildBookList());
        Book book = books.get(1);

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(URL+"/"+book.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("book successfully deleted"));

        mockMvc.perform(MockMvcRequestBuilders.get(URL+"?author="+book.getAuthor())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }
}