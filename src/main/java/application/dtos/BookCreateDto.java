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
	
	public BookCreateDto() {}
	
	public BookCreateDto(LocalDate date, UUID prisonerId, String isbn, String title, String author) {
		super();
		this.id = UUID.randomUUID();
		this.date = date;
		this.prisonerId = prisonerId;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
	}
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public UUID getPrisonerId() {
		return prisonerId;
	}

	public void setPrisonerId(UUID prisonerId) {
		this.prisonerId = prisonerId;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}