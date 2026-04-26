package application.repositories;

import domain.entities.Book;
import java.util.List;
import java.util.UUID;

public interface BookRepository {
    void add(Book book);
    List<Book> getAll();
    Book getById(UUID id);
    List<Book> getByPrisonerId(UUID prisonerId);
    void update(Book book);
    void delete(UUID id);
}