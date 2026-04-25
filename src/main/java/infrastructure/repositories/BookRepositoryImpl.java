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
    public void save(Book book) {
        String sql = """
            INSERT INTO Books (Id, PrisonerId, Date, Isbn, Title, Author)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getId().toString());
            stmt.setString(2, book.getPrisonerId().toString());
            stmt.setDate(3, Date.valueOf(book.getDate()));
            stmt.setString(4, book.getIsbn());
            stmt.setString(5, book.getTitle());
            stmt.setString(6, book.getAuthor());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> findByPrisonerId(UUID prisonerId) {
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