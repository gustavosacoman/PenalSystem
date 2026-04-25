package infrastructure;

import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/PenalSystem";
    private static final String USER = "root";
    private static final String PASSWORD = "root";


    public static void applyMigrations(){
        System.out.println("Synchronizing database with flyway");

        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(URL, USER, PASSWORD)
                    .load();

            flyway.migrate();

        }catch (Exception e)
        {
            System.err.println("Error Flyway: " + e.getMessage());
            throw e;
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            throw new SQLException("Mysql driver not found", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
