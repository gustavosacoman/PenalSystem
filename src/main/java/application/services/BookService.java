package application.services;

import application.dtos.BookCreateDto;
import domain.entities.Book;
import domain.entities.Prisoner;
import domain.exceptions.MaxNumberOfBooksException;
import infrastructure.repositories.BookRepositoryImpl;
import infrastructure.repositories.PrisonerRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class BookService {

    private final BookRepositoryImpl bookRepository;
    private final PrisonerRepository prisonerRepository;

    public BookService() {
        this.bookRepository = new BookRepositoryImpl();
        this.prisonerRepository = new PrisonerRepository();
    }

    public Book createBook(BookCreateDto dto) throws SQLException {

        if (dto == null || dto.getPrisonerId() == null) {
            throw new IllegalArgumentException("Invalid book creation request");
        }

        Prisoner prisoner = prisonerRepository.getPrisonerById(dto.getPrisonerId());
        int booksCounter = prisoner.getBooksCounter();

        if (prisoner.getCurrentYear() != LocalDate.now().getYear()) {
            prisoner.setBooksCounter(0);
        }

        if (booksCounter >= 12) {
            throw new MaxNumberOfBooksException();
        }

        Book book = new Book(
                UUID.randomUUID(),
                dto.getPrisonerId(),
                dto.getDate(),
                dto.getIsbn(),
                dto.getTitle(),
                dto.getAuthor()
        );

        bookRepository.add(book);

        prisoner.setBooksCounter(booksCounter + 1);
        prisoner.setUpdatedReleaseDate(prisoner.getUpdatedReleaseDate().minusDays(3));

        prisonerRepository.update(prisoner);

        return book;
    }

    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    public Book getById(UUID id) {
        return bookRepository.getById(id);
    }

    public List<Book> getByPrisonerId(UUID prisonerId) {
        if (prisonerId == null) {
            throw new IllegalArgumentException("Invalid prisoner ID");
        }

        return bookRepository.getByPrisonerId(prisonerId);
    }

    public Book updateBook(UUID bookId, BookCreateDto dto) throws SQLException {

        Book existing = bookRepository.getById(bookId);

        UUID oldPrisonerId = existing.getPrisonerId();
        UUID newPrisonerId = dto.getPrisonerId();

        existing.setTitle(dto.getTitle());
        existing.setAuthor(dto.getAuthor());
        existing.setIsbn(dto.getIsbn());

        if (!oldPrisonerId.equals(newPrisonerId)) {

            Prisoner oldPrisoner = prisonerRepository.getPrisonerById(oldPrisonerId);
            Prisoner newPrisoner = prisonerRepository.getPrisonerById(newPrisonerId);

            oldPrisoner.setBooksCounter(oldPrisoner.getBooksCounter() - 1);
            oldPrisoner.setUpdatedReleaseDate(oldPrisoner.getUpdatedReleaseDate().plusDays(3));

            if (newPrisoner.getCurrentYear() != LocalDate.now().getYear()) {
                newPrisoner.setBooksCounter(0);
            }

            if (newPrisoner.getBooksCounter() >= 12) {
                throw new MaxNumberOfBooksException();
            }

            newPrisoner.setBooksCounter(newPrisoner.getBooksCounter() + 1);
            newPrisoner.setUpdatedReleaseDate(newPrisoner.getUpdatedReleaseDate().minusDays(3));

            prisonerRepository.update(oldPrisoner);
            prisonerRepository.update(newPrisoner);

            existing.setPrisonerId(newPrisonerId);
        }

        bookRepository.update(existing);

        return existing;
    }

    public void deleteBook(UUID bookId) throws SQLException {

        Book book = bookRepository.getById(bookId);

        Prisoner prisoner = prisonerRepository.getPrisonerById(book.getPrisonerId());

        prisoner.setBooksCounter(prisoner.getBooksCounter() - 1);
        prisoner.setUpdatedReleaseDate(prisoner.getUpdatedReleaseDate().plusDays(3));

        prisonerRepository.update(prisoner);

        bookRepository.delete(bookId);
    }
}