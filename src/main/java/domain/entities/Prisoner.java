package domain.entities;

import java.time.LocalDate;
import java.util.UUID;

public class Prisoner {
    private UUID id;
    private String name;
    private LocalDate birthDate;
    private String cpf;
    private LocalDate arrivalDate;
    private LocalDate originalReleaseDate;
    private LocalDate updatedReleaseDate;
    private int booksCounter;
    private int currentYear;
    private String zipCode;
    private String streetNumber;
    private String street;
    private String city;
    private String state;

    public Prisoner(){}

    public Prisoner(String name, LocalDate birthDate,
                    String cpf, LocalDate arrivalDate, LocalDate originalReleaseDate,
                    LocalDate updatedReleaseDate, int booksCounter, int currentYear,
                    String zipCode, String streetNumber, String street, String city, String state)
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
        this.zipCode = zipCode;
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.state = state;
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

    public String getZipCode(){return zipCode;}

    public void setZipCode(String zipCode) {this.zipCode = zipCode;}
    public void setStreetNumber(String streetNumber) {this.streetNumber = streetNumber;}
    public void setStreet(String street) {this.street = street;}
    public void setCity(String city) {this.city = city;}
    public void setState(String state) {this.state = state;}

    public String getStreetNumber() {return streetNumber;}
    public String getStreet() {return street;}
    public String getCity() {return city;}
    public String getState() {return state;}
}
