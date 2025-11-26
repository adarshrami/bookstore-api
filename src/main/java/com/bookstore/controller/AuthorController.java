package com.bookstore.controller;

import com.bookstore.dto.AuthorDTO;
import com.bookstore.entity.Author;
import com.bookstore.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/author")
@Tag(name = "Author API", description = "Operations related to managing authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    @Operation(
            summary = "Add a new author",
            description = "Takes AuthorDTO as input and saves the author to the database"
    )
    public ResponseEntity<String> addAuthor(@RequestBody AuthorDTO authorReq) {

        Author author = new Author();
        author.setName(authorReq.getName());

        authorService.saveAuthor(author);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Author Added Successfully");
    }

    @GetMapping
    @Operation(
            summary = "Get All Authors"
    )
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing author",
            description = "Updates author details using AuthorDTO"
    )
    public ResponseEntity<String> updateAuthor(
            @PathVariable int id,
            @RequestBody AuthorDTO authorReq
    ) {
        Author author = authorService.updateAuthor(id, authorReq);
        if (author == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Author Not Found");
        }
        return ResponseEntity.ok("Author Updated Successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an author",
            description = "Deletes an author by ID"
    )
    public ResponseEntity<String> deleteAuthor(@PathVariable int id) {
        boolean deleted = authorService.deleteAuthor(id);

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Author Not Found");
        }

        return ResponseEntity.ok("Author Deleted Successfully");
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Author by ID",
            description = "Fetches a single author using its ID"
    )
    public ResponseEntity<?> getAuthorById(@PathVariable int id) {
        Author author = authorService.getAuthorById(id);

        if (author == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Author Not Found");
        }

        return ResponseEntity.ok(author);
    }


}




