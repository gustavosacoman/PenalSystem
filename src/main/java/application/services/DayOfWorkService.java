package application.services;

import application.dtos.DayOfWorkCreateDto;
import domain.entities.DayOfWork;
import domain.entities.Prisoner;
import infrastructure.repositories.DayOfWorkRepositoryImpl;

import java.sql.SQLException;
import java.util.List;
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
        if (dto == null || dto.getPrisonerId() == null || dto.getDate() == null) {
            throw new IllegalArgumentException("Invalid day of work creation request");
        }

        Prisoner prisoner;
        try {
            prisoner = prisonerService.getPrisonerById(dto.getPrisonerId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DayOfWork dayOfWork = new DayOfWork(
                UUID.randomUUID(),
                dto.getPrisonerId(),
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

    public DayOfWork getById(UUID id) {
        return dayOfWorkRepository.getById(id);
    }

    public List<DayOfWork> getByPrisonerId(UUID prisonerId) {
        if (prisonerId == null) {
            throw new IllegalArgumentException("Invalid prisoner ID");
        }

        return dayOfWorkRepository.getByPrisonerId(prisonerId);
    }

    public DayOfWork updateDayOfWork(UUID dayOfWorkId, DayOfWorkCreateDto dto) {
        if (dto == null || dto.getPrisonerId() == null || dto.getDate() == null) {
            throw new IllegalArgumentException("Invalid day of work update request");
        }

        DayOfWork existing = dayOfWorkRepository.getById(dayOfWorkId);
        UUID oldPrisonerId = existing.getPrisonerId();
        UUID newPrisonerId = dto.getPrisonerId();

        existing.setDescription(dto.getDescription());
        existing.setDate(dto.getDate());

        if (!oldPrisonerId.equals(newPrisonerId)) {
            Prisoner oldPrisoner;
            Prisoner newPrisoner;
            try {
                oldPrisoner = prisonerService.getPrisonerById(oldPrisonerId);
                newPrisoner = prisonerService.getPrisonerById(newPrisonerId);

                prisonerService.updateReleaseDateById(
                        oldPrisoner.getId(),
                        oldPrisoner.getUpdatedReleaseDate().plusDays(WORK_DAYS_REDUCTION));

                prisonerService.updateReleaseDateById(
                        newPrisoner.getId(),
                        newPrisoner.getUpdatedReleaseDate().minusDays(WORK_DAYS_REDUCTION));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            existing.setPrisonerId(newPrisonerId);
        }

        dayOfWorkRepository.update(existing);

        return existing;
    }

    public void deleteDayOfWork(UUID dayOfWorkId) {
        DayOfWork dayOfWork = dayOfWorkRepository.getById(dayOfWorkId);

        Prisoner prisoner;
        try {
            prisoner = prisonerService.getPrisonerById(dayOfWork.getPrisonerId());

            prisonerService.updateReleaseDateById(
                    prisoner.getId(),
                    prisoner.getUpdatedReleaseDate().plusDays(WORK_DAYS_REDUCTION));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        dayOfWorkRepository.delete(dayOfWorkId);
    }
}
