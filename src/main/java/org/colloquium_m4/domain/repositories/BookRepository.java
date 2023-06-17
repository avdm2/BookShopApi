package org.colloquium_m4.domain.repositories;

import org.colloquium_m4.domain.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
