package application.dtos;

import java.time.LocalDate;
import java.util.UUID;

public class StudyCreateDto {
	private UUID id;
	private UUID prisonerId;
	private String subject;
	private LocalDate date;

	public StudyCreateDto() {}

	public StudyCreateDto(UUID prisonerId,String subject, LocalDate date) {
		super();
		this.id = UUID.randomUUID();
		this.prisonerId = prisonerId;
		this.subject = subject;
		this.date = date;
	}
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getPrisonerId() {
		return prisonerId;
	}

	public void setPrisonerId(UUID prisonerId) {
		this.prisonerId = prisonerId;
	}

	public String getSubject() {return subject;}

	public void setSubject(String subject) {this.subject = subject;}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}