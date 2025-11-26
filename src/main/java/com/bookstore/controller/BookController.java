package com.bookstore.controller;

import com.bookstore.dto.BookDTO;
import com.bookstore.entity.Book;
import com.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
@Tag(name = "Book API", description = "CRUD operations for books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @Operation(summary = "Add a Book")
    public ResponseEntity<String> addBook(@RequestBody BookDTO bookDTO) {

        Book book = bookService.addBook(bookDTO);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Author with given ID does not exist!");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Book Added Successfully");
    }


    // Get All Books
    @GetMapping
    @Operation(summary = "Get All Books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // Get Book by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get Book by ID")
    public ResponseEntity<?> getBookById(@PathVariable int id) {

        Book book = bookService.getBookById(id);
        if (book == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Book Not Found");

        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Book by ID")
    public ResponseEntity<String> updateBook(
            @PathVariable int id,
            @RequestBody BookDTO bookDTO) {

        Book updated = bookService.updateBook(id, bookDTO);

        if (updated == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Book or Author not found! Cannot update.");
        }

        return ResponseEntity.ok("Book Updated Successfully");
    }


    // Delete Book
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Book by ID")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        boolean deleted = bookService.deleteBook(id);

        if (!deleted)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Book Not Found");

        return ResponseEntity.ok("Book Deleted Successfully");
    }

    @GetMapping("/page")
    @Operation(summary = "Get books with pagination and sorting")
    public ResponseEntity<Page<Book>> getBooksPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Page<Book> books = bookService.getBooksPage(page, size, sortBy, sortDir);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search")
    @Operation(summary = "Search books by title or author name")
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author
    ) {
        List<Book> books = bookService.searchBooks(title, author);
        return ResponseEntity.ok(books);
    }

}