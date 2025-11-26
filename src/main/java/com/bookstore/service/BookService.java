package com.bookstore.service;

import com.bookstore.dto.BookDTO;
import com.bookstore.entity.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {

    Book addBook(BookDTO bookDTO);

    Book updateBook(int id, BookDTO bookDTO);

    boolean deleteBook(int id);

    Book getBookById(int id);

    List<Book> getAllBooks();

    Page<Book> getBooksPage(int page, int size, String sortBy, String sortDir);

    public List<Book> searchBooks(String title, String author);
}
