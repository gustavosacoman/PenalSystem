package application.services;

import application.dtos.DayOfWorkCreateDto;
import application.dtos.DayOfWorkUpdateDto;
import domain.entities.DayOfWork;
import domain.entities.Prisoner;
import infrastructure.repositories.DayOfWorkRepositoryImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class DayOfWorkService {

    private static final int WORK_DAYS_REDUCTION = 1;

    private final DayOfWorkRepositoryImpl dayOfWorkRepository;
    private final PrisonerService prisonerService;

    public DayOfWorkService() {
        this.dayOfWorkRepository = new DayOfWorkRepositoryImpl();
        this.prisonerService = new PrisonerService();
    }

    public DayOfWork createDayOfWork(DayOfWorkCreateDto dto) {
        if (dto == null || dto.getCpf() == null || dto.getCpf().isBlank() || dto.getDate() == null) {
            throw new IllegalArgumentException("Invalid day of work creation request");
        }

        Prisoner prisoner;
        try {
            prisoner = prisonerService.getPrisonerByCpf(dto.getCpf());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DayOfWork dayOfWork = new DayOfWork(
                UUID.randomUUID(),
                prisoner.getId(),
                dto.getDate(),
                dto.getDescription());

        dayOfWorkRepository.add(dayOfWork);

        try {
            prisonerService.updateReleaseDateById(
                    prisoner.getId(),
                    prisoner.getUpdatedReleaseDate().minusDays(WORK_DAYS_REDUCTION));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dayOfWork;
    }

    public List<DayOfWork> getAll() {
        return dayOfWorkRepository.getAll();
    }

    public DayOfWork getById(UUID dayOfWorkId) {
        if (dayOfWorkId == null) {
            throw new IllegalArgumentException("Invalid day of work ID");
        }

        return dayOfWorkRepository.getById(dayOfWorkId);
    }

    public String getPrisonerCpfById(UUID prisonerId) {
        if (prisonerId == null) {
            throw new IllegalArgumentException("Invalid prisoner ID");
        }

        try {
            return prisonerService.getPrisonerById(prisonerId).getCpf();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DayOfWork> getByPrisonerCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("Invalid prisoner CPF");
        }

        try {
            prisonerService.getPrisonerByCpf(cpf);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Prisoner not found");
        }

        return dayOfWorkRepository.getByPrisonerCpf(cpf);
    }

    public DayOfWork updateDayOfWork(UUID dayOfWorkId, DayOfWorkUpdateDto dto) {
        if (dayOfWorkId == null) {
            throw new IllegalArgumentException("Invalid day of work ID");
        }
        if (dto == null) {
            throw new IllegalArgumentException("Invalid update payload");
        }

        DayOfWork dayOfWork = dayOfWorkRepository.getById(dayOfWorkId);

        if (dto.getDate() != null) {
            dayOfWork.setDate(dto.getDate());
        }
        if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
            dayOfWork.setDescription(dto.getDescription());
        }

        dayOfWorkRepository.update(dayOfWork);
        return dayOfWork;
    }

    public void deleteDayOfWork(UUID dayOfWorkId) {
        try {
            DayOfWork dayOfWork = dayOfWorkRepository.getById(dayOfWorkId);

            Prisoner prisoner = prisonerService.getPrisonerById(dayOfWork.getPrisonerId());

            prisonerService.updateReleaseDateById(
                    prisoner.getId(),
                    prisoner.getUpdatedReleaseDate().plusDays(WORK_DAYS_REDUCTION));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        dayOfWorkRepository.delete(dayOfWorkId);
    }
}
