package com.king.Bibliotheque.Repositories;

import com.king.Bibliotheque.Models.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Books, Integer> {
  Books findByTitle(String title);
}
