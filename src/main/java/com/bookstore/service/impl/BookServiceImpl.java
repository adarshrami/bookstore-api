package com.bookstore.service.impl;

import com.bookstore.Repository.AuthorRepository;
import com.bookstore.Repository.BookRepository;
import com.bookstore.dto.BookDTO;
import com.bookstore.entity.Author;
import com.bookstore.entity.Book;
import com.bookstore.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Book addBook(BookDTO bookDTO) {

        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElse(null);

        if (author == null) {
            return null;
        }

        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setAuthors(author);

        return bookRepository.save(book);
    }


    @Override
    public Book updateBook(int id, BookDTO bookDTO) {

        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return null;
        }

        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElse(null);

        if (author == null) {
            return null;
        }

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setAuthors(author);

        return bookRepository.save(book);
    }


    @Override
    public boolean deleteBook(int id) {
        if (!bookRepository.existsById(id)) return false;
        bookRepository.deleteById(id);
        return true;
    }

    @Override
    public Book getBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Page<Book> getBooksPage(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return bookRepository.findAll(pageable);
    }
    @Override
    public List<Book> searchBooks(String title, String author) {

        if (title != null) {
            return bookRepository.findByTitleContainingIgnoreCase(title);
        }
        if (author != null) {
            return bookRepository.findByAuthorContainingIgnoreCase(author);
        }
        return Collections.emptyList();
    }
}