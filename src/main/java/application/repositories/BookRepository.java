package application.repositories;

import domain.entities.Book;
import java.util.List;
import java.util.UUID;

public interface BookRepository {
    void save(Book book);
    List<Book> findByPrisonerId(UUID prisonerId);
}