package infrastructure.repositories;

import application.repositories.StudyRepository;
import domain.entities.Study;
import infrastructure.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudyRepositoryImpl implements StudyRepository {

    @Override
    public void add(Study study) {
        String sql = "INSERT INTO Studies (Id, Subject ,PrisonerId, Date) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, study.getId().toString());
            stmt.setString(2, study.getSubject());
            stmt.setString(5, study.getPrisonerId().toString());
            stmt.setTimestamp(6, Timestamp.valueOf(study.getDate().atStartOfDay()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Study> getAll() {
        String sql = "SELECT * FROM Studies";
        List<Study> study = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                study.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return study;
    }

    @Override
    public Study getById(UUID id) {
        String sql = "SELECT * FROM Studies WHERE Id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id.toString());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }

            throw new RuntimeException("Study not found");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Study> getByPrisonerId(UUID prisonerId) {
        String sql = "SELECT * FROM Studies WHERE PrisonerId = ?";

        List<Study> study = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, prisonerId.toString());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                study.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return study;
    }

    @Override
    public void update(Study study) {
        String sql = "UPDATE Studies SET Subject=?, PrisonerId=? WHERE Id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, study.getSubject());
            stmt.setString(2, study.getPrisonerId().toString());
            stmt.setString(3, study.getId().toString());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM Studies WHERE Id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id.toString());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Study mapRow(ResultSet rs) throws SQLException {
        return new Study(
            UUID.fromString(rs.getString("Id")),
            UUID.fromString(rs.getString("PrisonerId")),
                rs.getString("Subject"),
            rs.getDate("Date").toLocalDate()
        );
    }
}