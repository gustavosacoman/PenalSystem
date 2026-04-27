package application.services;

import application.dtos.StudyCreateDto;
import application.repositories.StudyRepository;
import infrastructure.repositories.StudyRepositoryImpl;
import domain.entities.Study;
import infrastructure.repositories.PrisonerRepository;

import java.util.List;
import java.util.UUID;

public class StudyService {

    private final StudyRepository studyRepository;
    private final PrisonerRepository prisonerRepository;

    public StudyService() {
        this.studyRepository = new StudyRepositoryImpl();
        this.prisonerRepository = new PrisonerRepository();
    }

    public Study createStudy(StudyCreateDto dto) {

        if (dto == null || dto.getPrisonerId() == null) {
            throw new IllegalArgumentException("Invalid study creation request");
        }

        // TODO: Estou implmentando para o Study ainda
        /*
        Prisoner prisoner = prisonerRepository.getById(dto.getPrisonerId());

        if (prisoner.getCurrentYear() != LocalDate.now().getYear()) {
            prisoner.setBooksCounter(0);
        }
        */

        Study study = new Study(
                UUID.randomUUID(),
                dto.getPrisonerId(),
                dto.getSubject(),
                dto.getDate()
        );

        studyRepository.add(study);
        return study;
    }

    public List<Study> getAll() {return studyRepository.getAll();}

    public Study getById(UUID id) {
        return studyRepository.getById(id);
    }

    public List<Study> getByPrisonerId(UUID prisonerId) {
        if (prisonerId == null) {
            throw new IllegalArgumentException("Invalid prisoner ID");
        }

        return studyRepository.getByPrisonerId(prisonerId);
    }

    public Study updateStudy(UUID studyId, StudyCreateDto dto) {

        Study existing = studyRepository.getById(studyId);

        UUID oldPrisonerId = existing.getPrisonerId();
        UUID newPrisonerId = dto.getPrisonerId();
        existing.setSubject(dto.getSubject());


        if (!oldPrisonerId.equals(newPrisonerId)) {

            // TODO: Descomentar depois do Sacoman implementar
            /*
            Prisoner oldPrisoner = prisonerRepository.getById(oldPrisonerId);
            Prisoner newPrisoner = prisonerRepository.getById(newPrisonerId);

            oldPrisoner.setBooksCounter(oldPrisoner.getBooksCounter() - 1);
            oldPrisoner.setUpdatedReleaseDate(oldPrisoner.getUpdatedReleaseDate().plusDays(3));

            if (newPrisoner.getCurrentYear() != LocalDate.now().getYear()) {
                newPrisoner.setBooksCounter(0);
            }

            if (newPrisoner.getBooksCounter() >= 12) {
                throw new MaxNumberOfBooksException();
            }

            newPrisoner.setBooksCounter(newPrisoner.getBooksCounter() + 1);
            newPrisoner.setUpdatedReleaseDate(newPrisoner.getUpdatedReleaseDate().minusDays(3));

            prisonerRepository.update(oldPrisoner);
            prisonerRepository.update(newPrisoner);

            */
            existing.setPrisonerId(newPrisonerId);
        }

        studyRepository.update(existing);

        return existing;
    }

    public void deleteStudy(UUID studyId) {

        Study study = studyRepository.getById(studyId);

         // TODO: Descomentar depois do Sacoman implementar
        /*
        Prisoner prisoner = prisonerRepository.getById(book.getPrisonerId());

        prisoner.setBooksCounter(prisoner.getBooksCounter() - 1);
        prisoner.setUpdatedReleaseDate(prisoner.getUpdatedReleaseDate().plusDays(3));

        prisonerRepository.update(prisoner);
         */

        studyRepository.delete(studyId);
    }
}