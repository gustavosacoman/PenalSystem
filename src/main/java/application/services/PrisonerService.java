package application.services;

import domain.entities.Prisoner;
import infrastructure.repositories.PrisonerRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

import application.dtos.CreatePrisonerDto;

public class PrisonerService {
    private final PrisonerRepository prisonerRepository = new PrisonerRepository();

    public void addPrisoner(CreatePrisonerDto addDto) throws SQLException{
        Prisoner prisoner = new Prisoner();

        prisoner.setId(UUID.randomUUID());
        prisoner.setName(addDto.name());
        prisoner.setCpf(addDto.cpf());
        prisoner.setBirthDate(addDto.birthDate());
        prisoner.setArrivalDate(addDto.arrivalDate());
        prisoner.setBooksCounter(0);
        prisoner.setCurrentYear(LocalDate.now().getYear());

        LocalDate calculatedRelease = addDto.arrivalDate().plusYears(addDto.sentenceYears());
        prisoner.setOriginalReleaseDate(calculatedRelease);
        prisoner.setUpdatedReleaseDate(calculatedRelease);

        prisonerRepository.add(prisoner);
    }



}
