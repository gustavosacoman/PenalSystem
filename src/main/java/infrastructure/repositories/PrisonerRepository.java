package infrastructure.repositories;

import infrastructure.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import domain.entities.Prisoner;

public class PrisonerRepository {

    public void add(Prisoner prisoner) throws SQLException {
        String sql = "INSERT INTO Prisoners(id, name, birth_date, cpf, arrival_date, " +
                "original_release_date, updated_release_date, books_counter, current_year)" +
                "VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, prisoner.getId().toString());
            stmt.setString(2, prisoner.getName());
            stmt.setDate(3, java.sql.Date.valueOf(prisoner.getBirthDate()));
            stmt.setString(4, prisoner.getCpf());
            stmt.setDate(5, java.sql.Date.valueOf(prisoner.getArrivalDate()));
            stmt.setDate(6, java.sql.Date.valueOf(prisoner.getOriginalReleaseDate()));
            stmt.setDate(7, java.sql.Date.valueOf(prisoner.getUpdatedReleaseDate()));
            stmt.setInt(8, prisoner.getBooksCounter());
            stmt.setInt(9, prisoner.getCurrentYear());

            stmt.executeUpdate();

        }
    }

}
