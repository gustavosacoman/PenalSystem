package infrastructure.repositories;

import infrastructure.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import domain.entities.Prisoner;

public class PrisonerRepository {

    public void add(Prisoner prisoner) throws SQLException {
        String sql = "INSERT INTO Prisoners(Id, Name, BirthDate, CPF, ArrivalDate, " +
                "OriginalReleaseDate, UpdatedReleaseDate, BooksCounter, CurrentYear)" +
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

    public void update(Prisoner p) throws SQLException{
        String query = "UPDATE Prisoners SET Name = ?, BirthDate = ?, " +
                "ArrivalDate = ?, OriginalReleaseDate = ?, UpdatedReleaseDate = ?, " +
                "BooksCounter = ?, CurrentYear = ? " +
                "WHERE id = ? OR cpf = ?";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, p.getName());
            stmt.setDate(2, java.sql.Date.valueOf(p.getBirthDate()));
            stmt.setDate(3, java.sql.Date.valueOf(p.getArrivalDate()));
            stmt.setDate(4, java.sql.Date.valueOf(p.getOriginalReleaseDate()));
            stmt.setDate(5, java.sql.Date.valueOf(p.getUpdatedReleaseDate()));
            stmt.setInt(6, p.getBooksCounter());
            stmt.setInt(7, p.getCurrentYear());

            stmt.setString(8, p.getId().toString());
            stmt.setString(9, p.getCpf());
            stmt.executeUpdate();
        }
    }

    public Prisoner getPrisonerById(UUID id) throws SQLException {
        String query = "SELECT * FROM Prisoners WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, id.toString());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Prisoner p = new Prisoner();
                p.setId(UUID.fromString(rs.getString("id")));
                p.setName(rs.getString("name"));
                p.setCpf(rs.getString("cpf"));

                p.setArrivalDate(rs.getDate("ArrivalDate").toLocalDate());
                p.setBirthDate(rs.getDate("BirthDate").toLocalDate());
                p.setOriginalReleaseDate(rs.getDate("OriginalReleaseDate").toLocalDate());
                p.setUpdatedReleaseDate(rs.getDate("UpdatedReleaseDate").toLocalDate());

                p.setBooksCounter(rs.getInt("BooksCounter"));
                p.setCurrentYear(rs.getInt("CurrentYear"));

                return p;
            }
        }
        return null;
    }

    public Prisoner getPrisonerBycpf(String cpf) throws SQLException{
        String query = "SELECT * from Prisoners WHERE cpf = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1,cpf);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Prisoner p = new Prisoner();
                p.setId(UUID.fromString(rs.getString("id")));
                p.setName(rs.getString("name"));
                p.setCpf(rs.getString("cpf"));

                p.setArrivalDate(rs.getDate("ArrivalDate").toLocalDate());
                p.setBirthDate(rs.getDate("BirthDate").toLocalDate());
                p.setOriginalReleaseDate(rs.getDate("OriginalReleaseDate").toLocalDate());
                p.setUpdatedReleaseDate(rs.getDate("UpdatedReleaseDate").toLocalDate());

                p.setBooksCounter(rs.getInt("BooksCounter"));
                p.setCurrentYear(rs.getInt("CurrentYear"));

                return p;
            }
        }
        return null;
    }

    public void DeletePrisonerBycpf(String cpf) throws  SQLException{

        String query = "DELETE FROM Prisoners Where cpf = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, cpf);

            stmt.executeUpdate();
        }
    }

    public void DeletePrisonerById(UUID id) throws  SQLException{

        String query = "DELETE FROM Prisoners Where id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, id.toString());

            stmt.executeUpdate();
        }
    }



}
