package application.repositories;

import domain.entities.Prisoner;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PrisonerRepository {
    void add(Prisoner prisoner) throws SQLException;
    void update(Prisoner prisoner) throws SQLException;
    List<Prisoner> getAll() throws SQLException;
    Prisoner getPrisonerById(UUID id) throws SQLException;
    void updateReleaseDateById(UUID prisonerId, LocalDate updatedReleaseDate) throws SQLException;
    Prisoner getPrisonerBycpf(String cpf) throws SQLException;
    void DeletePrisonerBycpf(String cpf) throws SQLException;
    void DeletePrisonerById(UUID id) throws SQLException;
}
