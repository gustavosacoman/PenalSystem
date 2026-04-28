package application.dtos;

import java.time.LocalDate;

public class DayOfWorkUpdateDto {
    private LocalDate date;
    private String description;

    public DayOfWorkUpdateDto() {
    }

    public DayOfWorkUpdateDto(LocalDate date, String description) {
        this.date = date;
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
