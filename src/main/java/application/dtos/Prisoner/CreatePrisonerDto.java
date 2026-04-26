package application.dtos.Prisoner;

import java.time.LocalDate;

public record CreatePrisonerDto (
    String name,
    String cpf,
    LocalDate birthDate,
    LocalDate arrivalDate,
    LocalDate originalReleaseDate,
    int currentYear
) {}