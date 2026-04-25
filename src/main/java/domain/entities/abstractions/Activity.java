package domain.entities.abstractions;

import java.time.LocalDate;
import java.util.UUID;

import domain.entities.Prisoner;

public abstract class Activity {
	private UUID id;
	private UUID prisonerId;
	private LocalDate date;
	
	private Prisoner prisoner;
	
	public Activity(UUID id, UUID prisonerId, LocalDate date) {
		super();
		this.id = id;
		this.prisonerId = prisonerId;
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

	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Prisoner getPrisoner() {
		return prisoner;
	}
	public void setPrisoner(Prisoner prisoner) {
		this.prisoner = prisoner;
	}
}
