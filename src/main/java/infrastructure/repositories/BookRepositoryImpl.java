package infrastructure.repositories;

import application.repositories.BookRepository;
import domain.entities.Book;
import infrastructure.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookRepositoryImpl implements BookRepository {

    @Override
    public void add(Book book) {
        String sql = "INSERT INTO Books (Id, Isbn, Title, Author, PrisonerId, Date) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getId().toString());
            stmt.setString(2, book.getIsbn());
            stmt.setString(3, book.getTitle());
            stmt.setString(4, book.getAuthor());
            stmt.setString(5, book.getPrisonerId().toString());
            stmt.setTimestamp(6, Timestamp.valueOf(book.getDate().atStartOfDay()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> getAll() {
        String sql = "SELECT * FROM Books";
        List<Book> books = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                books.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }

    @Override
    public Book getById(UUID id) {
        String sql = "SELECT * FROM Books WHERE Id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id.toString());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }

            throw new RuntimeException("Book not found");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> getByPrisonerId(UUID prisonerId) {
        String sql = "SELECT * FROM Books WHERE PrisonerId = ?";

        List<Book> books = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, prisonerId.toString());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                books.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }

    @Override
    public void update(Book book) {
        String sql = "UPDATE Books SET Isbn=?, Title=?, Author=?, PrisonerId=? WHERE Id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getIsbn());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getPrisonerId().toString());
            stmt.setString(5, book.getId().toString());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM Books WHERE Id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id.toString());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Book mapRow(ResultSet rs) throws SQLException {
        return new Book(
            UUID.fromString(rs.getString("Id")),
            UUID.fromString(rs.getString("PrisonerId")),
            rs.getDate("Date").toLocalDate(),
            rs.getString("Isbn"),
            rs.getString("Title"),
            rs.getString("Author")
        );
    }
}