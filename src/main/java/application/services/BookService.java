package application.services;

import application.dtos.BookCreateDto;
import domain.entities.Book;
import domain.entities.Prisoner;
import infrastructure.repositories.BookRepositoryImpl;
import infrastructure.repositories.PrisonerRepository;

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

    public Book createBook(BookCreateDto dto) {

        if (dto == null || dto.getPrisonerId() == null) {
            throw new IllegalArgumentException("Invalid book creation request");
        }

        // TODO: Descomentar depois do Sacoman implementar
        /*
        Prisoner prisoner = prisonerRepository.findById(dto.getPrisonerId());

        if (prisoner.getCurrentYear() != LocalDate.now().getYear()) {
            prisoner.setBookCounter(0);
        }

        if (prisoner.getBookCounter() >= 12) {
            throw new RuntimeException("Maximum number of books per year reached");
        }
        */

        Book book = new Book(
                UUID.randomUUID(),
                dto.getPrisonerId(),
                dto.getDate(),
                dto.getIsbn(),
                dto.getTitle(),
                dto.getAuthor()
        );

        bookRepository.save(book);

        // TODO: Descomentar depois do Sacoman implementar
        /*prisoner.setBookCounter(prisoner.getBookCounter() + 1);
        prisoner.reducePenalty(3);

        prisonerRepository.update(prisoner);*/

        return book;
    }

    public List<Book> getByPrisonerId(UUID prisonerId) {
        if (prisonerId == null) {
            throw new IllegalArgumentException("Invalid prisoner ID");
        }

        return bookRepository.findByPrisonerId(prisonerId);
    }
}