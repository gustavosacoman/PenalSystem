package infrastructure.repositories;

import application.repositories.DayOfWorkRepository;
import domain.entities.DayOfWork;
import infrastructure.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DayOfWorkRepositoryImpl implements DayOfWorkRepository {

    @Override
    public void add(DayOfWork dayOfWork) {
        String sql = "INSERT INTO DaysOfWork (Id, Description, PrisonerId, Date) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dayOfWork.getId().toString());
            stmt.setString(2, dayOfWork.getDescription());
            stmt.setString(3, dayOfWork.getPrisonerId().toString());
            stmt.setTimestamp(4, Timestamp.valueOf(dayOfWork.getDate().atStartOfDay()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DayOfWork> getAll() {
        String sql = "SELECT * FROM DaysOfWork";
        List<DayOfWork> daysOfWork = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                daysOfWork.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return daysOfWork;
    }

    @Override
    public DayOfWork getById(UUID id) {
        String sql = "SELECT * FROM DaysOfWork WHERE Id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id.toString());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }

            throw new RuntimeException("Day of work not found");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DayOfWork> getByPrisonerId(UUID prisonerId) {
        String sql = "SELECT * FROM DaysOfWork WHERE PrisonerId = ?";
        List<DayOfWork> daysOfWork = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, prisonerId.toString());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                daysOfWork.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return daysOfWork;
    }

    @Override
    public void update(DayOfWork dayOfWork) {
        String sql = "UPDATE DaysOfWork SET Description = ?, PrisonerId = ?, Date = ? WHERE Id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dayOfWork.getDescription());
            stmt.setString(2, dayOfWork.getPrisonerId().toString());
            stmt.setTimestamp(3, Timestamp.valueOf(dayOfWork.getDate().atStartOfDay()));
            stmt.setString(4, dayOfWork.getId().toString());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM DaysOfWork WHERE Id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id.toString());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private DayOfWork mapRow(ResultSet rs) throws SQLException {
        return new DayOfWork(
                UUID.fromString(rs.getString("Id")),
                UUID.fromString(rs.getString("PrisonerId")),
                rs.getDate("Date").toLocalDate(),
                rs.getString("Description"));
    }
}
