package domain.entities;

import java.time.LocalDate;
import java.util.UUID;

import domain.entities.abstractions.Activity;

public class DayOfWork extends Activity {
    private String description;

    public DayOfWork() {
    }

    public DayOfWork(UUID id, UUID prisonerId, LocalDate date, String description) {
        super(id, prisonerId, date);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
