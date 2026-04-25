package Application.Dtos;

import java.time.LocalDate;

public record CreatePrisonerDto (
    String name,
    String cpf,
    LocalDate birthDate,
    LocalDate arrivalDate,
    int sentenceYears
) {}