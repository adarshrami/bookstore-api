package com.bookstore.Repository;

import com.bookstore.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    public Optional<Author> findById(int id);
    public List<Author> findAll();
    public void deleteById(int id);
}
