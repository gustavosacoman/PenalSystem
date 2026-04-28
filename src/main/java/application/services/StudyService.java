package application.services;

import application.dtos.StudyCreateDto;
import application.repositories.StudyRepository;
import infrastructure.repositories.StudyRepositoryImpl;
import domain.entities.Prisoner;
import domain.entities.Study;
import infrastructure.repositories.PrisonerRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class StudyService {

    private final StudyRepository studyRepository;
    private final PrisonerRepository prisonerRepository;

    public StudyService() {
        this.studyRepository = new StudyRepositoryImpl();
        this.prisonerRepository = new PrisonerRepository();
    }

    public Study createStudy(StudyCreateDto dto) throws SQLException {

        if (dto == null || dto.getPrisonerId() == null) {
            throw new IllegalArgumentException("Invalid study creation request");
        }

        Prisoner prisoner = prisonerRepository.getPrisonerById(dto.getPrisonerId());

        Study study = new Study(
                UUID.randomUUID(),
                dto.getPrisonerId(),
                dto.getSubject(),
                dto.getDate()
        );

        studyRepository.add(study);

        prisoner.setUpdatedReleaseDate(prisoner.getUpdatedReleaseDate().minusDays(1));

        prisonerRepository.update(prisoner);

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

        existing.setSubject(dto.getSubject());
        studyRepository.update(existing);

        return existing;
    }

    public void deleteStudy(UUID studyId) throws SQLException {

        Study study = studyRepository.getById(studyId);

        Prisoner prisoner = prisonerRepository.getPrisonerById(study.getPrisonerId());

        prisoner.setUpdatedReleaseDate(prisoner.getUpdatedReleaseDate().plusDays(3));

        prisonerRepository.update(prisoner);

        studyRepository.delete(studyId);
    }
}