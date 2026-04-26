package application.dtos.Prisoner;

import java.time.LocalDate;

public record UpdatePrisonerDto (
        String name,
        LocalDate birthDate,
        LocalDate arrivalDate,
        LocalDate updatedReleaseDate,
        LocalDate originalReleaseDate,
        int booksCounter,
        int currentYear
){}