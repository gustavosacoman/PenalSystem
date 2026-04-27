package application.services;

import application.dtos.Prisoner.UpdatePrisonerDto;
import domain.entities.Prisoner;
import infrastructure.repositories.PrisonerRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

import application.dtos.Prisoner.CreatePrisonerDto;

public class PrisonerService {
    private final PrisonerRepository prisonerRepository = new PrisonerRepository();

    public Prisoner getPrisonerById(UUID id) throws SQLException{
        if (id == null){
            throw new NullPointerException("Id cannot be null");
        }
        var prisoner = prisonerRepository.getPrisonerById(id);

        if (prisoner == null){
            throw new NullPointerException("Not found");
        }
        return prisoner;
    }

    public Prisoner getPrisonerByCpf(String cpf) throws SQLException {
        if (cpf == null || cpf.isBlank()){
            throw new NullPointerException("cpf cannot be null");
        }

        var prisoner = prisonerRepository.getPrisonerBycpf(cpf);
        if (prisoner == null){
            throw new NullPointerException("Not found");
        }
        return prisoner;
    }


    public void addPrisoner(CreatePrisonerDto addDto) throws SQLException{

        Prisoner alreadyExist = prisonerRepository.getPrisonerBycpf(addDto.cpf());

        if (alreadyExist != null)
            throw new IllegalArgumentException("prisoner already exist!");

        Prisoner prisoner = new Prisoner();

        if (addDto.arrivalDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Arrival date cannot be in the past.");
        }

        if (addDto.originalReleaseDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Original release date cannot be in the past.");
        }

        prisoner.setId(UUID.randomUUID());
        prisoner.setName(addDto.name());
        prisoner.setCpf(addDto.cpf());
        prisoner.setBirthDate(addDto.birthDate());
        prisoner.setArrivalDate(addDto.arrivalDate());
        prisoner.setBooksCounter(0);
        prisoner.setCurrentYear(LocalDate.now().getYear());

        prisoner.setOriginalReleaseDate(addDto.originalReleaseDate());
        prisoner.setUpdatedReleaseDate(addDto.originalReleaseDate());

        prisonerRepository.add(prisoner);
    }

    public void updatePrisoner(UpdatePrisonerDto dto, UUID id, String cpf) throws  SQLException{

        if (dto == null)
            throw new NullPointerException("dto cannot be null");

        if (id == null && cpf == null)
            throw new IllegalArgumentException("you must need inform cpf or id");

        Prisoner actualPrisoner = null;
        if (id != null)
            actualPrisoner = prisonerRepository.getPrisonerById(id);
        else
            actualPrisoner = prisonerRepository.getPrisonerBycpf(cpf);

        if (actualPrisoner == null)
            throw new IllegalArgumentException("Prisoner not found");

        if (dto.name() != null && !dto.name().isBlank())
            actualPrisoner.setName(dto.name());

        if (dto.arrivalDate() != null)
            actualPrisoner.setArrivalDate(dto.arrivalDate());

        if (dto.updatedReleaseDate() != null)
            actualPrisoner.setUpdatedReleaseDate(dto.updatedReleaseDate());

        if (dto.birthDate() != null)
            actualPrisoner.setBirthDate(dto.birthDate());

        if (dto.currentYear() != 0)
            actualPrisoner.setCurrentYear(dto.currentYear());

        if (dto.booksCounter() != 0)
            actualPrisoner.setBooksCounter(dto.booksCounter());

        if (dto.originalReleaseDate() != null)
            actualPrisoner.setOriginalReleaseDate(dto.originalReleaseDate());

        prisonerRepository.update(actualPrisoner);
    }

    public void DeletePrisonerByCpfOrId(String cpf, UUID id) throws  SQLException{

        if (id == null && cpf.isBlank())
        {
            throw new NullPointerException("you need to specified the cpf or id");
        }

        if (id != null) {
            prisonerRepository.DeletePrisonerById(id);
        }
        else {
            prisonerRepository.DeletePrisonerBycpf(cpf);
        }

    }
    public void DeletePrisonerById(UUID id) throws  SQLException{

        if (id == null)
        {
            throw new NullPointerException("cpf cannot be null or blank");
        }

        prisonerRepository.DeletePrisonerById(id);
    }

}
