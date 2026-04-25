package Application.Services;

import Application.Dtos.CreatePrisonerDto;
import Domain.Entities.Prisoner;
import infrastructure.Repositories.PrisonerRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

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
