package com.book.repo;

import com.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book,Integer> {
    Optional<Book> findByName(String name);

    @Query(value = "select publisher,group_concat(name) from Book group by publisher",nativeQuery = true)
    List<Object[]> getBookDetails();

    List<Book> findByNameContaining(String word);
}
