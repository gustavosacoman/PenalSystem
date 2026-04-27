package application.repositories;

import domain.entities.DayOfWork;

import java.util.List;
import java.util.UUID;

public interface DayOfWorkRepository {
    void add(DayOfWork dayOfWork);
    List<DayOfWork> getAll();
    DayOfWork getById(UUID id);
    List<DayOfWork> getByPrisonerCpf(String cpf);
    void delete(UUID id);
}
