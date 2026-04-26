package application.services;

import application.dtos.DayOfWorkCreateDto;
import domain.entities.DayOfWork;
import infrastructure.repositories.DayOfWorkRepositoryImpl;

import java.util.List;
import java.util.UUID;

public class DayOfWorkService {

    private static final int WORK_DAYS_REDUCTION = 1;

    private final DayOfWorkRepositoryImpl dayOfWorkRepository;

    public DayOfWorkService() {
        this.dayOfWorkRepository = new DayOfWorkRepositoryImpl();
    }

    public DayOfWork createDayOfWork(DayOfWorkCreateDto dto) {
        if (dto == null || dto.getPrisonerId() == null || dto.getDate() == null) {
            throw new IllegalArgumentException("Invalid day of work creation request");
        }

        // TODO: Descomentar depois de implementarem PrisonerRepository (getById/update)
        /*
         * Prisoner prisoner = prisonerRepository.getById(dto.getPrisonerId());
         */

        DayOfWork dayOfWork = new DayOfWork(
                UUID.randomUUID(),
                dto.getPrisonerId(),
                dto.getDate(),
                dto.getDescription());

        dayOfWorkRepository.add(dayOfWork);

        // TODO: Descomentar depois de implementarem PrisonerRepository (getById/update)
        /*
         * prisoner.setUpdatedReleaseDate(prisoner.getUpdatedReleaseDate().minusDays(
         * WORK_DAYS_REDUCTION));
         * prisonerRepository.update(prisoner);
         */

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
            // TODO: Descomentar depois de implementarem PrisonerRepository (getById/update)
            /*
             * Prisoner oldPrisoner = prisonerRepository.getById(oldPrisonerId);
             * Prisoner newPrisoner = prisonerRepository.getById(newPrisonerId);
             * 
             * oldPrisoner.setUpdatedReleaseDate(oldPrisoner.getUpdatedReleaseDate().
             * plusDays(WORK_DAYS_REDUCTION));
             * newPrisoner.setUpdatedReleaseDate(newPrisoner.getUpdatedReleaseDate().
             * minusDays(WORK_DAYS_REDUCTION));
             * 
             * prisonerRepository.update(oldPrisoner);
             * prisonerRepository.update(newPrisoner);
             */
            existing.setPrisonerId(newPrisonerId);
        }

        dayOfWorkRepository.update(existing);

        return existing;
    }

    public void deleteDayOfWork(UUID dayOfWorkId) {
        DayOfWork dayOfWork = dayOfWorkRepository.getById(dayOfWorkId);

        // TODO: Descomentar depois de implementarem PrisonerRepository (getById/update)
        /*
         * Prisoner prisoner = prisonerRepository.getById(dayOfWork.getPrisonerId());
         * 
         * prisoner.setUpdatedReleaseDate(prisoner.getUpdatedReleaseDate().plusDays(
         * WORK_DAYS_REDUCTION));
         * prisonerRepository.update(prisoner);
         */

        dayOfWorkRepository.delete(dayOfWorkId);
    }
}
