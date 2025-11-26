package com.bookstore.service;

import com.bookstore.dto.AuthorDTO;
import com.bookstore.entity.Author;

import java.util.List;

public interface AuthorService {

    public void saveAuthor(Author author);
    public Author getAuthor(int id);
    public List<Author> getAllAuthors();
    public boolean deleteAuthor(int id);
    public Author updateAuthor(int id, AuthorDTO author);
    Author getAuthorById(int id);


}
