package application.repositories;

import domain.entities.Study;
import java.util.List;
import java.util.UUID;

public interface StudyRepository {
    void add(Study study);
    List<Study> getAll();
    Study getById(UUID id);
    List<Study> getByPrisonerId(UUID prisonerId);
    void update(Study study);
    void delete(UUID id);
}