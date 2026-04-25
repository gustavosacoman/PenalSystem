package application.dtos;

import java.time.LocalDate;
import java.util.UUID;

public class BookCreateDto {
	private UUID id;
	private LocalDate date;
	private UUID prisonerId;
	private String isbn;
	private String title;
	private String author;
	
	public BookCreateDto(LocalDate date, UUID prisonerId, String isbn, String title, String author) {
		super();
		this.id = UUID.randomUUID();
		this.date = date;
		this.prisonerId = prisonerId;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
	}
}