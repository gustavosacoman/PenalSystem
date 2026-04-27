package application.dtos;

import java.time.LocalDate;

public class DayOfWorkCreateDto {
    private LocalDate date;
    private String cpf;
    private String description;

    public DayOfWorkCreateDto() {
    }

    public DayOfWorkCreateDto(LocalDate date, String cpf, String description) {
        this.date = date;
        this.cpf = cpf;
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
