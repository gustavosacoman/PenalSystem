package application.dtos;

import java.time.LocalDate;
import java.util.UUID;

public class DayOfWorkCreateDto {
    private LocalDate date;
    private UUID prisonerId;
    private String description;

    public DayOfWorkCreateDto() {
    }

    public DayOfWorkCreateDto(LocalDate date, UUID prisonerId, String description) {
        this.date = date;
        this.prisonerId = prisonerId;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
