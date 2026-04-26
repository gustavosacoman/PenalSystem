package domain.entities;

import domain.entities.abstractions.Activity;

import java.time.LocalDate;
import java.util.UUID;

public class Study extends Activity {

    private String Subject;

    public Study() {}

    public Study(UUID id, UUID prisonerId, String Subject, LocalDate date) {
        super(id, prisonerId, date);
        this.Subject = Subject;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

}
