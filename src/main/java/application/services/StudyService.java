package application.services;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import application.dtos.StudyCreateDto;
import application.events.ActivityType;
import application.events.ReleaseDateUpdatedEvent;
import application.messaging.EventPublisher;
import application.repositories.PrisonerRepository;
import application.repositories.StudyRepository;
import domain.entities.Prisoner;
import domain.entities.Study;

public class StudyService {

    private final StudyRepository studyRepository;
    private final PrisonerRepository prisonerRepository;
    private final EventPublisher eventPublisher;

    public StudyService(StudyRepository studyRepository, PrisonerRepository prisonerRepository, EventPublisher eventPublisher) {
        this.studyRepository = studyRepository;
        this.prisonerRepository = prisonerRepository;
        this.eventPublisher = eventPublisher;
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

        eventPublisher.publish(new ReleaseDateUpdatedEvent(
                prisoner.getId(),
                prisoner.getUpdatedReleaseDate(),
                ActivityType.STUDY,
                1
        ));

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

        prisoner.setUpdatedReleaseDate(prisoner.getUpdatedReleaseDate().plusDays(1));

        prisonerRepository.update(prisoner);

        studyRepository.delete(studyId);
    }
}