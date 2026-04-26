package domain.entities;

import java.time.LocalDate;
import java.util.UUID;

public class Prisoner {
    public UUID id;
    public String name;
    public LocalDate birthDate;
    public String cpf;
    public LocalDate arrivalDate;
    public LocalDate originalReleaseDate;
    public LocalDate updatedReleaseDate;
    public int booksCounter;
    public int currentYear;


    public Prisoner(){}

    public Prisoner(String name, LocalDate birthDate,
                    String cpf, LocalDate arrivalDate, LocalDate originalReleaseDate,
                    LocalDate updatedReleaseDate, int booksCounter, int currentYear)
    {
        this.id = UUID.randomUUID();
        this.name = name;
        this.birthDate = birthDate;
        this.cpf = cpf;
        this.arrivalDate = arrivalDate;
        this.originalReleaseDate = originalReleaseDate;
        this.updatedReleaseDate = updatedReleaseDate;
        this.booksCounter = booksCounter;
        this.currentYear = currentYear;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public int getBooksCounter() {
        return booksCounter;
    }

    public void setBooksCounter(int booksCounter) {
        this.booksCounter = booksCounter;
    }

    public LocalDate getUpdatedReleaseDate() {
        return updatedReleaseDate;
    }

    public void setUpdatedReleaseDate(LocalDate updatedReleaseDate) {
        this.updatedReleaseDate = updatedReleaseDate;
    }

    public LocalDate getOriginalReleaseDate() {
        return originalReleaseDate;
    }

    public void setOriginalReleaseDate(LocalDate originalReleaseDate) {
        this.originalReleaseDate = originalReleaseDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
