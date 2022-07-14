package com.book.repo;

import com.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book,Integer> {
    Optional<Book> findByName(String name);
}
